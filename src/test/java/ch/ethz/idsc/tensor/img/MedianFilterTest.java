// code by gjoel and jph
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

  public void testMatrix() {
    Tensor input = Tensors.fromString("{{1, 5, 3, 1, 2, 1, 1, 2, 0}, {3, 2, 3, 0, 0, 1, 1, 9, 1}, {3, 2, 3, 1123, 2, 3, 1, 2, 23}}");
    assertEquals(input, MedianFilter.of(input, 0));
    {
      Tensor result = MedianFilter.of(input, 1);
      String mathematica = "{{5/2, 3, 5/2, 3/2, 1, 1, 1, 1, 3/2}, {5/2, 3, 3, 2, 1, 1, 1, 1,  2}, {5/2, 3, 5/2, 5/2, 3/2, 1, 3/2, 3/2, 11/2}}";
      assertEquals(result, Tensors.fromString(mathematica));
    }
    {
      Tensor result = MedianFilter.of(input, 2);
      String mathematica = "{{3, 3, 2, 2, 1, 1, 1, 1, 1}, {3, 3, 2, 2, 1, 1, 1, 1, 1}, {3, 3, 2, 2, 1, 1, 1, 1, 1}}";
      assertEquals(result, Tensors.fromString(mathematica));
    }
    {
      Tensor result = MedianFilter.of(input, 3);
      String mathematica = "{{3, 2, 2, 2, 2, 2, 1, 1, 1}, {3, 2, 2, 2, 2, 2, 1, 1, 1}, {3, 2, 2, 2, 2, 2, 1, 1, 1}}";
      assertEquals(result, Tensors.fromString(mathematica));
    }
  }

  public void testEmpty() {
    Tensor input = Tensors.empty();
    Tensor result = MedianFilter.of(input, 2);
    input.append(RealScalar.ZERO);
    assertEquals(result, Tensors.empty());
  }

  public void testDemo() {
    Tensor vector = Tensors.vector(0, 0, 1, 0, 0, 0, 0, 3, 3, 3, 0);
    Tensor result = MedianFilter.of(vector, 2);
    assertEquals(result, Tensors.fromString("{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3}"));
    result = MedianFilter.of(vector, 1);
    assertEquals(result, Tensors.fromString("{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3/2}"));
  }

  public void testScalarFail() {
    try {
      MedianFilter.of(RealScalar.of(3), 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRadiusFail() {
    try {
      MedianFilter.of(Tensors.vector(1, 2, 3, 4), -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
