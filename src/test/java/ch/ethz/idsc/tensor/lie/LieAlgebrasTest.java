// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dot;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class LieAlgebrasTest extends TestCase {
  public void testHeisenberg() {
    Tensor he3 = LieAlgebras.heisenberg3();
    Tensor eye = IdentityMatrix.of(3);
    assertEquals(Dot.of(he3, eye.get(0), eye.get(1)), eye.get(2));
    assertEquals(Dot.of(he3, eye.get(1), eye.get(0)), eye.get(2).negate());
    assertEquals(JacobiIdentity.of(he3), Array.zeros(3, 3, 3, 3));
  }

  public void testSe2Matrix() {
    Tensor bx = Tensors.fromString("{{0,0,1},{0,0,0},{0,0,0}}");
    Tensor by = Tensors.fromString("{{0,0,0},{0,0,1},{0,0,0}}");
    Tensor bt = Tensors.fromString("{{0,-1,0},{1,0,0},{0,0,0}}");
    assertEquals(LieAlgebras.bracketMatrix(bx, by), Array.zeros(3, 3));
    assertEquals(LieAlgebras.bracketMatrix(bt, bx), by);
    assertEquals(LieAlgebras.bracketMatrix(by, bt), bx);
  }

  public void testSe2() {
    Tensor se2 = LieAlgebras.se2().unmodifiable();
    assertEquals(JacobiIdentity.of(se2), Array.zeros(3, 3, 3, 3));
    assertEquals(se2.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 1)), Array.zeros(3));
    assertEquals(se2.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 1).negate());
    assertEquals(se2.dot(UnitVector.of(3, 1)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 0).negate());
    assertEquals(KillingForm.of(se2), DiagonalMatrix.of(0, 0, 2));
  }

  public void testBracket() {
    Tensor matrix = LieAlgebras.bracketMatrix(LieAlgebras.so3().get(0), LieAlgebras.so3().get(1));
    assertEquals(matrix, LieAlgebras.so3().get(2));
  }

  public void testBracketVectorFail() {
    try {
      LieAlgebras.bracketMatrix(Tensors.empty(), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      LieAlgebras.bracketMatrix(Tensors.vector(1, 2), Tensors.vector(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBracketMatrixFail() {
    try {
      LieAlgebras.bracketMatrix(RotationMatrix.of(RealScalar.ONE), Tensors.vector(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBracketAdFail() {
    try {
      LieAlgebras.bracketMatrix(LieAlgebras.so3(), LieAlgebras.so3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
