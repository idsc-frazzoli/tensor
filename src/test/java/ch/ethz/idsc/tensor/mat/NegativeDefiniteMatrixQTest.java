// code by jph
package ch.ethz.idsc.tensor.mat;

import junit.framework.TestCase;

public class NegativeDefiniteMatrixQTest extends TestCase {
  public void testDiagonal() {
    assertTrue(NegativeDefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(-1, -3, -4)));
  }
}
