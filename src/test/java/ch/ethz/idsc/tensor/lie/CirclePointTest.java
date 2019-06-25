// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Optional;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class CirclePointTest extends TestCase {
  public void testExact() {
    for (int count = 0; count < 12; ++count) {
      Scalar scalar = RationalScalar.of(count, 12);
      Optional<Tensor> optional = CirclePoint.INSTANCE.turns(scalar);
      assertTrue(optional.isPresent());
      Tensor vector = optional.get();
      assertTrue(0 < vector.stream().filter(ExactScalarQ::of).count());
      Chop._14.requireClose(vector, AngleVector.of(scalar.multiply(Pi.TWO)));
    }
  }
}
