// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class SymmetricMatrixQTest extends TestCase {
  public void testHilbert() {
    assertTrue(SymmetricMatrixQ.of(HilbertMatrix.of(10)));
  }

  public void testHilbert2() {
    assertFalse(SymmetricMatrixQ.of(HilbertMatrix.of(2, 3)));
  }

  public void testNumeric() {
    Tensor matrix = Tensors.fromString("{{1, 2.000000000000001}, {2, 1}}");
    assertTrue(SymmetricMatrixQ.of(matrix));
    assertFalse(SymmetricMatrixQ.of(matrix, Chop.NONE));
  }

  public void testVector() {
    assertFalse(SymmetricMatrixQ.of(Tensors.vector(1, 2, 3)));
  }

  public void testScalar() {
    assertFalse(SymmetricMatrixQ.of(RealScalar.ONE));
  }

  public void testEmpty() {
    assertFalse(SymmetricMatrixQ.of(Tensors.empty()));
  }

  public void testFailNull() {
    try {
      SymmetricMatrixQ.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
