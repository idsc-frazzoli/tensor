// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.TensorScalarFunction;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import junit.framework.TestCase;

public class ImageFilterTest extends TestCase {
  private static final TensorScalarFunction MIN = block -> block.flatten(-1).reduce(Min::of).get().Get();
  private static final TensorScalarFunction MAX = block -> block.flatten(-1).reduce(Max::of).get().Get();

  public void testMin() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor tensor = RandomVariate.of(distribution, 20, 30);
    Tensor filter = MinFilter.of(tensor, 3);
    Tensor result = ImageFilter.of(tensor, 3, MIN);
    assertEquals(filter, result);
  }

  public void testMax() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor tensor = RandomVariate.of(distribution, 10, 15);
    Tensor filter = MaxFilter.of(tensor, 3);
    Tensor result = ImageFilter.of(tensor, 3, MAX);
    assertEquals(filter, result);
  }

  public void testEmpty() {
    Tensor result = ImageFilter.of(Tensors.empty(), 3, MAX);
    assertEquals(result, Tensors.empty());
  }

  public void testRadiusFail() {
    try {
      ImageFilter.of(Tensors.empty(), -1, MAX);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      ImageFilter.of(RealScalar.ONE, 1, MAX);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFunctionNullFail() {
    try {
      ImageFilter.of(Tensors.empty(), 3, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
