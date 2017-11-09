// code by gjoel
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.img.MeanFilter;
import ch.ethz.idsc.tensor.img.MedianFilter;
import junit.framework.TestCase;

public class MedianFilterTest extends TestCase {
  public void testId() {
    Tensor vector1 = Tensors.vector(1,2,3,4,5,6);
    Tensor result1 = MedianFilter.of(vector1, 0);
    assertEquals(vector1, result1);
  }

  public void testMedian() {
    Tensor vector1 = Tensors.vector(1.0, 2.0, 3.0, 2.0, 1.0);
    Tensor result1 = MedianFilter.of(vector1, 1);
    assertEquals(Tensors.vector(1.5, 2.0, 2.0, 2.0, 1.5), result1);
    Tensor vector2 = Tensors.vector(1.0, 2.0, 4.0, 8.0, 16.0, 32.0, 64.0, 128.0, 256.0);
    Tensor result2 = MedianFilter.of(vector2, 2);
    assertEquals(Tensors.vector(2.0, 3.0, 4.0, 8.0, 16.0, 32.0, 64.0, 96.0, 128.0), result2);
  }

  public void testMean() {
    Tensor vector1 = Tensors.vector(1.0, 4.0, 4.0, 1.0);
    Tensor result1 = MeanFilter.of(vector1, 1);
    assertEquals(Tensors.vector(2.5, 3.0, 3.0, 2.5), result1);
    Tensor vector2 = Tensors.vector(5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 40.0, 45.0, 50.0);
    Tensor result2 = MeanFilter.of(vector2, 2);
    assertEquals(Tensors.vector(10.0, 12.5, 15.0, 20.0, 26.0, 32.0, 38.0, 41.25, 45.0), result2);
  }
}
