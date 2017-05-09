// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class PositiveSemidefiniteMatrixQTest extends TestCase {
  public void testDiagonal() {
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(3, 2, 0, 1)));
    assertFalse(PositiveSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(3, -2, 0, 1)));
  }

  public void testZeros() {
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(Array.zeros(4, 4)));
  }
}
