// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import junit.framework.TestCase;

public class ToeplitzMatrixTest extends TestCase {
  public void testSimple() {
    Tensor matrix = ToeplitzMatrix.of(Tensors.vector(1, 2, 3, 4, 5));
    assertTrue(SquareMatrixQ.of(matrix));
    // System.out.println(Pretty.of(matrix));
  }

  public void testSymmetric() {
    Tensor matrix = ToeplitzMatrix.of(Tensors.vector(5, 4, 3, 4, 5));
    assertTrue(SymmetricMatrixQ.of(matrix));
  }

  public void testFailEven() {
    try {
      ToeplitzMatrix.of(Tensors.vector(1, 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailEmpty() {
    try {
      ToeplitzMatrix.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
