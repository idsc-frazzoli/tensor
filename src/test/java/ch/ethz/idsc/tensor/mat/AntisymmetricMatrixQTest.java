// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.lie.Cross;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import junit.framework.TestCase;

public class AntisymmetricMatrixQTest extends TestCase {
  public void testMatrix() {
    assertTrue(AntisymmetricMatrixQ.of(Array.zeros(4, 4)));
    assertFalse(AntisymmetricMatrixQ.of(IdentityMatrix.of(3)));
    assertTrue(AntisymmetricMatrixQ.of(Tensors.fromString("{{0,1},{-1,0}}")));
  }

  public void testCross() {
    assertTrue(AntisymmetricMatrixQ.of(Cross.of(Tensors.vector(1, 2, 3))));
  }

  public void testNonMatrix() {
    assertFalse(AntisymmetricMatrixQ.of(RealScalar.ONE));
    assertFalse(AntisymmetricMatrixQ.of(LieAlgebras.sl2()));
  }
}
