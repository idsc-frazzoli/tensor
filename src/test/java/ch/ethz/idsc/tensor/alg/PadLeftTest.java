// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PadLeftTest extends TestCase {
  public void testVectorLo() {
    Tensor vector = Tensors.vector(1, 2, 3, 4, 5, 6);
    Tensor result = PadLeft.of(vector, 10);
    assertEquals(result.extract(4, 10), vector);
    assertEquals(result.extract(0, 4), Array.zeros(4));
  }

  public void testVectorHi() {
    Tensor vector = Tensors.vector(1, 2, 3, 4, 5, 6);
    Tensor result = PadLeft.of(vector, 4);
    assertEquals(result, vector.extract(2, 6));
  }

  public void testMatrixRegular() {
    Tensor vector = Tensors.fromString("{{1,2,3}}");
    Tensor result = PadLeft.of(vector, 2, 4);
    assertEquals(result, Tensors.fromString("{{0, 0, 0, 0}, {0, 1, 2, 3}}"));
  }

  public void testMatrixIrregular1() {
    Tensor vector = Tensors.fromString("{{1,2,3},{4,5}}");
    Tensor result = PadLeft.of(vector, 3, 4);
    assertEquals(result, Tensors.fromString("{{0, 0, 0, 0}, {0, 1, 2, 3}, {0, 0, 4, 5}}"));
  }

  public void testMatrixIrregular2() {
    Tensor vector = Tensors.fromString("{{1,2,3},{4,5}}");
    Tensor result = PadLeft.of(vector, 1, 2);
    assertEquals(result, Tensors.fromString("{{4, 5}}"));
  }

  public void testMatrixIrregular3() {
    Tensor vector = Tensors.fromString("{{1},{2},{4,5}}");
    Tensor result = PadLeft.of(vector, 2, 2);
    assertEquals(result, Tensors.fromString("{{0, 2}, {4, 5}}"));
  }

  public void testFail() {
    try {
      PadLeft.of(Tensors.fromString("{{1},{2},{4,5}}"), 2, 2, 6);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      PadLeft.of(Tensors.vector(1, 2, 3), -2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
