// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class NegativeDefiniteMatrixQTest extends TestCase {
  public void testDiagonal() {
    assertTrue(NegativeDefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(-1, -3, -4)));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(-1, 0, -4)));
  }

  public void testComplex() {
    assertTrue(NegativeDefiniteMatrixQ.ofHermitian(Tensors.fromString("{{-10,I},{-I,-10}}")));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(Tensors.fromString("{{-10,I},{-I,-1/10}}")));
    assertTrue(NegativeDefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{-10,I},{-I,-10}}"))));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{-10,I},{-I,-1/10}}"))));
  }

  public void testVector() {
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(Tensors.vector(1, 2, 3)));
  }
}
