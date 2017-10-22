// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class NegativeSemidefiniteMatrixQTest extends TestCase {
  public void testDiagonal() {
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(-1, -3, -4)));
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(-1, 0, -4)));
  }

  public void testComplex() {
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(Tensors.fromString("{{-10,I},{-I,-10}}")));
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(Tensors.fromString("{{-10,I},{-I,-1/10}}")));
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{-10,I},{-I,-10}}"))));
    assertTrue(NegativeSemidefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{-10,I},{-I,-1/10}}"))));
  }

  public void testVector() {
    assertFalse(NegativeSemidefiniteMatrixQ.ofHermitian(Tensors.vector(1, 2, 3)));
  }
}
