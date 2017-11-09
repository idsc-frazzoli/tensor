// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MeanFilterTest extends TestCase {
  public void testId() {
    Tensor vector1 = Tensors.vector(1, 2, 3, 4, 5, 6);
    Tensor result1 = MeanFilter.of(vector1, 0);
    assertEquals(vector1, result1);
  }

  public void testMean1() {
    Tensor vector1 = Tensors.vector(1, 4, 4, 1);
    Tensor result1 = MeanFilter.of(vector1, 1);
    assertEquals(Tensors.vector(2.5, 3, 3, 2.5), result1);
  }

  public void testMean2() {
    Tensor vector2 = Tensors.vector(5, 10, 15, 20, 25, 30, 40, 45, 50);
    Tensor result2 = MeanFilter.of(vector2, 2);
    assertEquals(Tensors.vector(10, 12.5, 15, 20, 26, 32, 38, 41.25, 45), result2);
  }

  public void testEmpty() {
    assertEquals(MeanFilter.of(Tensors.empty(), 2), Tensors.empty());
  }
}
