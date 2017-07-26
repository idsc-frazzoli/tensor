// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Multinomial;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.CholeskyDecomposition;
import ch.ethz.idsc.tensor.mat.ConjugateTranspose;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.HermitianMatrixQ;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.mat.NegativeDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NegativeSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NullSpace;
import ch.ethz.idsc.tensor.mat.PositiveDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.PositiveSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.RowReduce;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class Quantity4Test extends TestCase {
  public void testMultinomial() {
    Scalar qs1;
    {
      qs1 = Quantity.of(-4, "[m*s]");
    }
    Scalar qs2 = Quantity.of(3, "[m]");
    Scalar val = Quantity.of(2, "[s]");
    Scalar res = Multinomial.horner(Tensors.of(qs1, qs2), val);
    assertEquals(res.toString(), "2[m*s]");
  }

  public void testLinearSolve() {
    final Scalar one = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(2), "[m]");
    Scalar qs4 = Quantity.of(RealScalar.of(-3), "[m]");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2, one);
    Tensor inv = LinearSolve.of(mat, eye);
    Tensor res = mat.dot(inv);
    assertEquals(res, eye);
  }

  public void testInverse2() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(3), "[rad]");
    Scalar qs4 = Quantity.of(RealScalar.of(4), "[rad]");
    Tensor ve1 = Tensors.of(qs1.multiply(qs1), qs2.multiply(qs3));
    Tensor ve2 = Tensors.of(qs2.multiply(qs3), qs4.multiply(qs4));
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2); // <- yey!
    {
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Inverse.of(mat);
    }
  }

  public void testInverse3() {
    Tensor mat = Tensors.fromString( //
        "{{1[m^2], 2[m*rad], 3[kg*m]}, {4[m*rad], 2[rad^2], 2[kg*rad]}, {5[kg*m], 1[kg*rad], 7[kg^2]}}", //
        Quantity::fromString);
    {
      Tensor eye = IdentityMatrix.of(3);
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Tensor eye = IdentityMatrix.of(3);
      Tensor inv = LinearSolve.withoutAbs(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Tensor inv = Inverse.of(mat);
      assertEquals(mat.dot(inv), inv.dot(mat));
      assertEquals(mat.dot(inv), IdentityMatrix.of(3));
    }
    assertFalse(HermitianMatrixQ.of(mat));
    assertFalse(SymmetricMatrixQ.of(mat));
  }

  public void testCholesky2() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
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

  public void testCholesky3() {
    Tensor mat = Tensors.fromString( //
        "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}", //
        Quantity::fromString);
    {
      Tensor eye = IdentityMatrix.of(3);
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Tensor inv = Inverse.of(mat);
      assertEquals(mat.dot(inv), inv.dot(mat));
      assertEquals(mat.dot(inv), IdentityMatrix.of(3));
    }
    {
      CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
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

  public void testCholeskyComplex() {
    Tensor mat = Tensors.fromString("{{10[m^2],I[m*kg]},{-I[m*kg],10[kg^2]}}", Quantity::fromString);
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

  public void testLinearSolve1() {
    Scalar qs1 = Quantity.of(3, "[m]");
    Scalar qs2 = Quantity.of(4, "[s]");
    Tensor ve1 = Tensors.of(qs1);
    Tensor mat = Tensors.of(ve1);
    Tensor rhs = Tensors.of(qs2);
    Tensor sol = LinearSolve.of(mat, rhs);
    Tensor res = mat.dot(sol);
    assertEquals(res, rhs);
  }

  public void testRowReduce1() {
    Tensor ve1 = Tensors.of(Quantity.of(1, "[m]"), Quantity.of(2, "[m]"));
    Tensor ve2 = Tensors.of(Quantity.of(2, "[m]"), Quantity.of(10, "[m]"));
    Tensor nul = RowReduce.of(Tensors.of(ve1, ve2));
    assertEquals(nul, IdentityMatrix.of(2)); // consistent with Mathematica
  }

  public void testRowReduce2() {
    Tensor ve1 = Tensors.of(Quantity.of(1, "[m]"), Quantity.of(2, "[m]"));
    Tensor nul = RowReduce.of(Tensors.of(ve1, ve1));
    assertEquals(nul, Tensors.fromString("{{1, 2}, {0[m], 0[m]}}", Quantity::fromString));
    assertEquals(nul, Tensors.fromString("{{1, 2}, {0, 0}}"));
  }

  public void testNullspace() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1);
    Tensor nul = NullSpace.usingRowReduce(mat);
    // System.out.println(nul);
    assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }
}
