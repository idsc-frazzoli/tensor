// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class PositiveSemidefiniteMatrixQTest extends TestCase {
  public void testDiagonal() {
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(3, 2, 0, 1)));
    assertFalse(PositiveSemidefiniteMatrixQ.ofHermitian(DiagonalMatrix.of(3, -2, 0, 1)));
  }

  public void testZeros() {
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(Array.zeros(4, 4)));
  }

  public void testComplex() {
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(Tensors.fromString("{{10,I},{-I,10}}")));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(Tensors.fromString("{{10,I},{-I,1/10}}")));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{10,I},{-I,10}}"))));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(N.DOUBLE.of(Tensors.fromString("{{10,I},{-I,1/10}}"))));
  }

  public void testVector() {
    assertFalse(PositiveSemidefiniteMatrixQ.ofHermitian(Tensors.vector(1, 2, 3)));
  }
}
