// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.CholeskyDecomposition;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.mat.NegativeDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NegativeSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NullSpace;
import ch.ethz.idsc.tensor.mat.PositiveDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.PositiveSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.RowReduce;
import ch.ethz.idsc.tensor.opt.ConvexHull;
import junit.framework.TestCase;

public class QuantityScalar2Test extends TestCase {
  public void testLinearSolve() {
    final Scalar one = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Scalar qs4 = QuantityScalar.of(RealScalar.of(-3), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2, one);
    Tensor inv = LinearSolve.of(mat, eye);
    Tensor res = mat.dot(inv);
    assertEquals(res, eye);
  }

  public void testLinearSolve1() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "s", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1);
    Tensor mat = Tensors.of(ve1);
    Tensor rhs = Tensors.of(qs2);
    Tensor sol = LinearSolve.of(mat, rhs);
    Tensor res = mat.dot(sol);
    assertEquals(res, rhs);
  }

  public void testRowReduce() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1, qs2);
    // Tensor ve2 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1, ve1);
    // Tensor nul = RowReduce.of(Transpose.of(mat));
    Tensor nul = RowReduce.of(mat);
    // System.out.println(nul);
    // assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }

  public void testNullspace() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1);
    Tensor nul = NullSpace.of(mat);
    assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }

  public void testCholesky() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs2, qs1);
    Tensor ve2 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1, ve2);
    CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
    assertTrue(Scalars.nonZero(cd.det()));
    assertEquals(cd.diagonal().toString(), "{2[m^1], 3/2[m^1]}");
    assertTrue(PositiveDefiniteMatrixQ.ofHermitian(mat));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeSemidefiniteMatrixQ.ofHermitian(mat));
  }

  public void testConvexHull() {
    // final Scalar one = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Scalar qs4 = QuantityScalar.of(RealScalar.of(-3), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve2, ve1);
    Tensor hul = ConvexHull.of(mat);
    assertEquals(hul, mat);
  }
}
