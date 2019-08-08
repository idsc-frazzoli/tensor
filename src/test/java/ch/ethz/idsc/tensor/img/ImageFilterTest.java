// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import junit.framework.TestCase;

public class ImageFilterTest extends TestCase {
  public void testMin() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor tensor = RandomVariate.of(distribution, 20, 30);
    Tensor filter = MinFilter.of(tensor, 3);
    Tensor result = ImageFilter.of(tensor, 3, block -> block.flatten(-1).reduce(Min::of).get());
    assertEquals(filter, result);
  }

  public void testMax() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor tensor = RandomVariate.of(distribution, 10, 15);
    Tensor filter = MaxFilter.of(tensor, 3);
    Tensor result = ImageFilter.of(tensor, 3, block -> block.flatten(-1).reduce(Max::of).get());
    assertEquals(filter, result);
  }

  public void testEmpty() {
    Tensor result = ImageFilter.of(Tensors.empty(), 3, block -> block.flatten(-1).reduce(Max::of).get());
    assertEquals(result, Tensors.empty());
  }

  public void testRadiusFail() {
    try {
      ImageFilter.of(Tensors.empty(), -1, block -> block.flatten(-1).reduce(Max::of).get());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
