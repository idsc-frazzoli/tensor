// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ArrayPadTest extends TestCase {
  public void testVector() {
    Tensor vec = Tensors.vector(2, 3, -3, 1);
    Tensor pad = ArrayPad.of(vec, Arrays.asList(3), Arrays.asList(4));
    Tensor actual = Tensors.vector(0, 0, 0, 2, 3, -3, 1, 0, 0, 0, 0);
    assertEquals(pad, actual);
  }

  public void testMatrix() {
    Tensor matrix = Tensors.of(Tensors.vector(2, 3, 1), Tensors.vector(7, 8, 9));
    assertEquals(Dimensions.of(matrix), Arrays.asList(2, 3));
    Tensor pad = ArrayPad.of(matrix, Arrays.asList(1, 2), Arrays.asList(3, 4));
    assertEquals(Dimensions.of(pad), Arrays.asList(1 + 2 + 3, 2 + 3 + 4));
  }

  public void testForm() {
    Tensor matrix = Tensors.of(Tensors.vector(2, 3, 1), Tensors.vector(7, 8, 9));
    Tensor form = Tensors.of(matrix, matrix, matrix, matrix);
    assertEquals(Dimensions.of(form), Arrays.asList(4, 2, 3));
    Tensor pad = ArrayPad.of(form, Arrays.asList(2, 1, 2), Arrays.asList(1, 3, 4));
    assertEquals(Dimensions.of(pad), Arrays.asList(2 + 4 + 1, 1 + 2 + 3, 2 + 3 + 4));
  }

  public void testNonArray() {
    Tensor tensor = Tensors.fromString("{{1,2},{3}}");
    Tensor vector = ArrayPad.of(tensor, Arrays.asList(2), Arrays.asList(3));
    assertEquals(vector.length(), 2 + 2 + 3);
  }

  public void testFail() {
    Tensor vector = Tensors.vector(2, 3, -3, 1);
    try {
      ArrayPad.of(vector, Arrays.asList(1), Arrays.asList(-2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ArrayPad.of(vector, Arrays.asList(-1), Arrays.asList(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
