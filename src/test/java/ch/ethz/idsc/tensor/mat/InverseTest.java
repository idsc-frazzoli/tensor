// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class InverseTest extends TestCase {
  public void testInverse() {
    int n = 25;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(random.nextGaussian()), n, n);
    Tensor Ai = Inverse.of(A);
    assertEquals(A.dot(Ai).subtract(IdentityMatrix.of(A.length())).map(Chop._10), //
        Array.zeros(Dimensions.of(A)));
    assertEquals(Ai.dot(A).subtract(IdentityMatrix.of(A.length())).map(Chop._10), //
        Array.zeros(Dimensions.of(A)));
  }

  public void testInverseNoAbs() {
    int n = 12;
    int p = 20357;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> GaussScalar.of(random.nextInt(p), p), n, n);
    Tensor b = Tensors.vector(i -> GaussScalar.of(random.nextInt(p), p), n);
    Tensor x = LinearSolve.withoutAbs(A, b);
    assertEquals(A.dot(x), b);
    Tensor id = IdentityMatrix.of(n, GaussScalar.of(1, p));
    {
      Tensor Ai = LinearSolve.withoutAbs(A, id);
      assertEquals(A.dot(Ai), id);
      assertEquals(Ai.dot(A), id);
    }
    {
      Tensor Ai = LinearSolve.of(A, id);
      assertEquals(A.dot(Ai), id);
      assertEquals(Ai.dot(A), id);
    }
  }

  public void testGeneralIdentity() {
    Tensor A = HilbertMatrix.of(3, 3);
    Tensor b = UnitVector.of(3, 1);
    Tensor x = LinearSolve.of(A, b);
    assertEquals(A.dot(x), b);
    assertEquals(Inverse.of(A).dot(b), x);
  }

  public void testDet0() {
    Tensor matrix = ResourceData.of("/mat/det0-matlab.csv"); // det(matrix) == 0
    assertNotNull(matrix);
    try {
      Inverse.of(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Inverse.of(N.DOUBLE.of(matrix));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNonSquare() {
    try {
      Inverse.of(HilbertMatrix.of(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Inverse.of(HilbertMatrix.of(4, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank3() {
    try {
      Inverse.of(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(2, "m");
    Scalar qs3 = Quantity.of(3, "rad");
    Scalar qs4 = Quantity.of(4, "rad");
    Tensor ve1 = Tensors.of(qs1.multiply(qs1), qs2.multiply(qs3));
    Tensor ve2 = Tensors.of(qs2.multiply(qs3), qs4.multiply(qs4));
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2); // <- yey!
    Tensor inv = LinearSolve.of(mat, eye);
    Tensor res = mat.dot(inv);
    assertTrue(Chop.NONE.close(eye, res));
    Tensor inverse = Inverse.of(mat);
    Tensor expected = Tensors.fromString( //
        "{{-4/5[m^-2], 3/10[m^-1*rad^-1]}, {3/10[m^-1*rad^-1], -1/20[rad^-2]}}");
    assertEquals(inverse, expected);
    assertTrue(ExactScalarQ.all(inverse));
  }

  public void testQuantity2() {
    Tensor matrix = Tensors.fromString( //
        "{{1[m^2], 2[m*rad], 3[kg*m]}, {4[m*rad], 2[rad^2], 2[kg*rad]}, {5[kg*m], 1[kg*rad], 7[kg^2]}}");
    final Tensor eye = IdentityMatrix.of(3).unmodifiable();
    {
      Tensor inv = LinearSolve.of(matrix, eye);
      Tensor res = matrix.dot(inv);
      assertTrue(Chop.NONE.close(eye, res));
    }
    {
      Tensor inv = LinearSolve.withoutAbs(matrix, eye);
      Tensor res = matrix.dot(inv);
      assertTrue(Chop.NONE.close(eye, res));
    }
    {
      Tensor inv = Inverse.of(matrix);
      assertTrue(Chop.NONE.close(matrix.dot(inv), inv.dot(matrix)));
      assertTrue(Chop.NONE.close(matrix.dot(inv), IdentityMatrix.of(3)));
    }
    assertFalse(HermitianMatrixQ.of(matrix));
    assertFalse(SymmetricMatrixQ.of(matrix));
  }
}
