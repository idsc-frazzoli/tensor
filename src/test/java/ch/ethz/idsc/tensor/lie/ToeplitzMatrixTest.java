// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import junit.framework.TestCase;

public class ToeplitzMatrixTest extends TestCase {
  public void testSquare() {
    Tensor matrix = ToeplitzMatrix.of(Tensors.vector(1, 2, 3, 4, 5));
    assertTrue(SquareMatrixQ.of(matrix));
    assertEquals(matrix.get(0), Range.of(3, 6));
    assertEquals(matrix.get(1), Range.of(2, 5));
    assertEquals(matrix.get(2), Range.of(1, 4));
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
