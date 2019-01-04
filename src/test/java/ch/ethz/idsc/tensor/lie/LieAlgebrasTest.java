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
    Tensor ad = LieAlgebras.he1();
    Tensor eye = IdentityMatrix.of(3);
    assertEquals(Dot.of(ad, eye.get(0), eye.get(1)), eye.get(2));
    assertEquals(Dot.of(ad, eye.get(1), eye.get(0)), eye.get(2).negate());
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
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
    Tensor ad = LieAlgebras.se2().unmodifiable();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
    assertEquals(ad.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 1)), Array.zeros(3));
    assertEquals(ad.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 1).negate());
    assertEquals(ad.dot(UnitVector.of(3, 1)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 0).negate());
    assertEquals(KillingForm.of(ad), DiagonalMatrix.of(0, 0, 2));
  }

  public void testBracket() {
    Tensor matrix = LieAlgebras.bracketMatrix(LieAlgebras.so3().get(0), LieAlgebras.so3().get(1));
    assertEquals(matrix, LieAlgebras.so3().get(2));
  }

  public void testBracketVectorFail() {
    try {
      LieAlgebras.bracketMatrix(Tensors.empty(), Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      LieAlgebras.bracketMatrix(Tensors.vector(1, 2), Tensors.vector(3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBracketMatrixFail() {
    Tensor x = RotationMatrix.of(RealScalar.ONE);
    Tensor y = Tensors.vector(3, 4);
    try {
      LieAlgebras.bracketMatrix(x, y);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      LieAlgebras.bracketMatrix(y, x);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBracketAdFail() {
    try {
      LieAlgebras.bracketMatrix(LieAlgebras.so3(), LieAlgebras.so3());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBracketAdVectorFail() {
    Tensor x = LieAlgebras.so3();
    Tensor y = Tensors.vector(1, 2, 3);
    try {
      LieAlgebras.bracketMatrix(x, y);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      LieAlgebras.bracketMatrix(y, x);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
