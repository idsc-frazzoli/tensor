// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class IdentityMatrixTest extends TestCase {
  public void testOneQuantity() {
    Tensor matrix = IdentityMatrix.of(2, Quantity.of(1, "s"));
    assertEquals(matrix, Tensors.fromString("{{1[s], 0[s]}, {0[s], 1[s]}}"));
  }

  public void testFailZero() {
    try {
      IdentityMatrix.of(0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNegative() {
    try {
      IdentityMatrix.of(-3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailOneZero() {
    try {
      IdentityMatrix.of(0, Quantity.of(1, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailOneNegative() {
    try {
      IdentityMatrix.of(-3, Quantity.of(1, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
