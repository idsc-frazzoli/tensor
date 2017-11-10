// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MedianFilterTest extends TestCase {
  public void testId() {
    Tensor vector1 = Tensors.vector(1, 2, 3, 4, 5, 6);
    Tensor result1 = MedianFilter.of(vector1, 0);
    assertEquals(vector1, result1);
  }

  public void testMedian1() {
    Tensor vector1 = Tensors.vector(1, 2, 3, 2, 1);
    Tensor result1 = MedianFilter.of(vector1, 1);
    assertEquals(Tensors.vector(1.5, 2, 2, 2, 1.5), result1);
  }

  public void testMedian2() {
    Tensor vector2 = Tensors.vector(1, 2, 4, 8, 16, 32, 64, 128, 256);
    Tensor result2 = MedianFilter.of(vector2, 2);
    assertEquals(Tensors.vector(2, 3, 4, 8, 16, 32, 64, 96, 128), result2);
  }

  public void testEmpty() {
    Tensor input = Tensors.empty();
    Tensor result = MedianFilter.of(input, 2);
    input.append(RealScalar.ZERO);
    assertEquals(result, Tensors.empty());
  }
}
