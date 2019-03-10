// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import junit.framework.TestCase;

public class SpatialMedianTest extends TestCase {
  public void testSimple() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 0) //
    );
    Tensor sol = SpatialMedian.of(tensor).get();
    assertEquals(sol, Tensors.vector(0, 0));
  }

  public void testMathematica() {
    Tensor points = Tensors.fromString("{{1, 3, 5}, {7, 1, 2}, {9, 3, 1}, {4, 5, 6}}");
    Tensor solution = Tensors.vector(5.6583732018553249826, 2.7448562522811917613, 3.2509991568890024191);
    Optional<Tensor> uniform = SpatialMedian.of(points);
    assertTrue(Chop._08.close(uniform.get(), solution));
  }

  public void testMathematicaWeighted() {
    Tensor points = Tensors.fromString("{{1, 3, 5}, {-4, 1, 2}, {3, 3, 1}, {4, 5, 6}}");
    Tensor weights = Tensors.vector(1, 3, 4, 5);
    Optional<Tensor> weighted = SpatialMedian.with(0).weighted(points, weights);
    Tensor solution = Tensors.vector(2.3866562926712105936, 3.5603713896189638861, 3.5379382804133292184);
    assertTrue(Chop._08.close(weighted.get(), solution));
  }

  public void testPoles() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 10), //
        Tensors.vector(2, -10) //
    );
    SpatialMedian fermatWeberProblem = SpatialMedian.with(RealScalar.of(1e-2));
    Tensor sol = fermatWeberProblem.uniform(tensor).get();
    assertTrue(Norm._2.between(sol, tensor.get(1)).Get().number().doubleValue() < 2e-2);
  }

  public void testWeighted() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 10), //
        Tensors.vector(2, -10) //
    );
    SpatialMedian fermatWeberProblem = SpatialMedian.with(1e-10);
    Tensor weights = Tensors.vector(10, 1, 1, 1);
    Tensor sol = fermatWeberProblem.weighted(tensor, weights).get();
    assertTrue(Norm._2.between(sol, tensor.get(0)).Get().number().doubleValue() < 2e-2);
  }

  public void testQuantity() {
    int present = 0;
    for (int count = 0; count < 10; ++count) {
      Tensor tensor = RandomVariate.of(UniformDistribution.unit(), 20, 2).map(value -> Quantity.of(value, "m"));
      SpatialMedian fermatWeberProblem = SpatialMedian.with(Quantity.of(1e-10, "m"));
      Optional<Tensor> optional = fermatWeberProblem.uniform(tensor);
      if (optional.isPresent()) {
        ++present;
        Tensor weiszfeld = optional.get();
        Clip clip = Clips.interval(Quantity.of(0, "m"), Quantity.of(1, "m"));
        clip.requireInside(weiszfeld.Get(0));
        clip.requireInside(weiszfeld.Get(1));
      }
    }
    assertTrue(5 < present);
  }
}
