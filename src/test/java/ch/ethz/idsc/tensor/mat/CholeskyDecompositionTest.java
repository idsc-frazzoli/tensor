// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class CholeskyDecompositionTest extends TestCase {
  static CholeskyDecomposition checkDecomp(Tensor A) {
    int n = A.length();
    CholeskyDecomposition cd = CholeskyDecomposition.of(A);
    Tensor res = cd.getL().dot(cd.diagonal().pmul(ConjugateTranspose.of(cd.getL())));
    assertEquals(Chop._12.of(A.subtract(res)), Array.zeros(n, n));
    assertEquals(Chop._12.of(cd.det().subtract(Det.of(A))), RealScalar.ZERO);
    return cd;
  }

  public void testRosetta1() {
    // +5 0 0
    // +3 3 0
    // -1 1 3
    // {{5, 3, -1}, {0, 3, 1}, {0, 0, 3}}
    checkDecomp(Tensors.matrix(new Number[][] { //
        { 25, 15, -5 }, //
        { 15, 18, 0 }, //
        { -5, 0, 11 } //
    }));
  }

  public void testWikiEn() throws Exception {
    CholeskyDecomposition cd = //
        checkDecomp(Tensors.matrix(new Number[][] { //
            { 4, 12, -16 }, //
            { 12, 37, -43 }, //
            { -16, -43, 98 } //
        }));
    Tensor ltrue = Tensors.matrix(new Number[][] { //
        { 1, 0, 0 }, //
        { 3, 1, 0 }, //
        { -4, 5, 1 } //
    });
    assertEquals(cd.getL(), ltrue);
    assertEquals(cd.diagonal(), Tensors.vector(4, 1, 9));
    Serialization.of(cd);
    assertTrue(Chop.NONE.allZero(UpperTriangularize.of(cd.getL(), 1)));
  }

  public void testMathematica1() {
    checkDecomp(Tensors.matrix(new Number[][] { //
        { 2, 1 }, //
        { 1, 2 } //
    }));
  }

  public void testMathematica2() {
    checkDecomp(Tensors.fromString("{{4, 3, 2, 1}, {3, 4, 3, 2}, {2, 3, 4, 3}, {+1, 2, 3, 4}}"));
    checkDecomp(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 4}}"));
    checkDecomp(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 0}}"));
  }

  public void testFail1() {
    try {
      checkDecomp(Tensors.fromString("{{4, 2}, {1, 4}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      checkDecomp(Tensors.fromString("{{4, I}, {I, 4}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testHilbert1() {
    checkDecomp(HilbertMatrix.of(10));
  }

  public void testHilbertN1() {
    checkDecomp(N.DOUBLE.of(HilbertMatrix.of(16)));
  }

  public void testDiag() {
    checkDecomp(DiagonalMatrix.of(1, -2, 3, -4));
    checkDecomp(DiagonalMatrix.of(0, -2, 3, -4));
    checkDecomp(DiagonalMatrix.of(0, -2, 3, -4));
    checkDecomp(DiagonalMatrix.of(0, -2, 0, -4));
    checkDecomp(DiagonalMatrix.of(1, 0, 3, -4));
    checkDecomp(DiagonalMatrix.of(1, -2, 3, 0));
    checkDecomp(DiagonalMatrix.of(1, 0, 3, 0));
  }

  public void testZeros1() {
    checkDecomp(Array.zeros(1, 1));
    checkDecomp(Array.zeros(5, 5));
  }

  public void testComplex() {
    checkDecomp(Tensors.fromString("{{10,I},{-I,10}}"));
    checkDecomp(N.DOUBLE.of(Tensors.fromString("{{10,I},{-I,10}}")));
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(2, "m");
    Tensor ve1 = Tensors.of(qs2, qs1);
    Tensor ve2 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1, ve2);
    CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
    assertTrue(Scalars.nonZero(cd.det()));
    assertEquals(cd.diagonal().toString(), "{2[m], 3/2[m]}");
    assertTrue(SymmetricMatrixQ.of(mat));
    assertTrue(HermitianMatrixQ.of(mat));
    assertTrue(PositiveDefiniteMatrixQ.ofHermitian(mat));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeSemidefiniteMatrixQ.ofHermitian(mat));
  }

  public void testQuantity2() {
    Tensor mat = Tensors.fromString( //
        "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}");
    {
      Tensor eye = IdentityMatrix.of(3);
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertTrue(Chop.NONE.close(eye, res));
      // assertEquals(eye, res);
      // assertEquals(res, eye);
    }
    {
      Tensor inv = Inverse.of(mat);
      // assertEquals(mat.dot(inv), inv.dot(mat));
      assertTrue(Chop.NONE.close(mat.dot(inv), inv.dot(mat)));
      // assertEquals(mat.dot(inv), IdentityMatrix.of(3));
      assertTrue(Chop.NONE.close(mat.dot(inv), IdentityMatrix.of(3)));
    }
    {
      CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
      // System.out.println(Det.of(mat));
      assertEquals(Det.of(mat), cd.det()); // 100[kg^2,m^2,rad^2]
      Tensor lower = rows_pmul_v(cd.getL(), Sqrt.of(cd.diagonal()));
      Tensor upper = Sqrt.of(cd.diagonal()).pmul(ConjugateTranspose.of(cd.getL()));
      Tensor res = lower.dot(upper);
      assertTrue(Chop._10.close(mat, res));
    }
    assertTrue(SymmetricMatrixQ.of(mat));
    assertTrue(HermitianMatrixQ.of(mat));
    assertTrue(PositiveDefiniteMatrixQ.ofHermitian(mat));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeSemidefiniteMatrixQ.ofHermitian(mat));
  }

  private static Tensor rows_pmul_v(Tensor L, Tensor diag) {
    return TensorMap.of(row -> row.pmul(diag), L, 1); // apply pmul on level 1
  }

  public void testQuantityComplex() {
    Tensor mat = Tensors.fromString("{{10[m^2],I[m*kg]},{-I[m*kg],10[kg^2]}}");
    CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
    Tensor sdiag = Sqrt.of(cd.diagonal());
    Tensor upper = sdiag.pmul(ConjugateTranspose.of(cd.getL()));
    {
      Tensor res = ConjugateTranspose.of(upper).dot(upper);
      assertTrue(Chop._10.close(mat, res));
    }
    {
      // the construction of the lower triangular matrix L . L* is not so convenient
      // Tensor lower = Transpose.of(sdiag.pmul(Transpose.of(cd.getL())));
      Tensor lower = rows_pmul_v(cd.getL(), sdiag);
      Tensor res = lower.dot(upper);
      assertTrue(Chop._10.close(mat, res));
    }
  }
}
