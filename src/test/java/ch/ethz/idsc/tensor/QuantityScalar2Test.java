// code by jph
package ch.ethz.idsc.tensor;

import java.util.HashMap;
import java.util.Map;

import ch.ethz.idsc.tensor.alg.Multinomial;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.alg.Transpose;
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
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.red.Quantile;
import junit.framework.TestCase;

public class QuantityScalar2Test extends TestCase {
  public void testMultinomial() {
    Scalar qs1;
    {
      Map<String, Scalar> map = new HashMap<>();
      map.put("m", RealScalar.ONE);
      map.put("s", RealScalar.ONE);
      qs1 = QuantityScalar.of(RealScalar.of(-4), new UnitMap(map));
    }
    Scalar qs2 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar val = QuantityScalar.of(RealScalar.of(2), "s", RealScalar.ONE);
    Scalar res = Multinomial.horner(Tensors.of(qs1, qs2), val);
    // System.out.println(res);
    assertEquals(res.toString(), "2[m^1,s^1]");
  }

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
    nul.map(a -> a);
    // System.out.println(nul);
    // assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }

  public void testNullspace() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1);
    Tensor nul = NullSpace.of(mat);
    // System.out.println(nul);
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

  public void testQuantile() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor vector = Tensors.of(qs1, qs2, qs3);
    assertEquals(Quantile.of(vector, RealScalar.ZERO), qs1);
    assertEquals(Quantile.of(vector, RealScalar.ONE), qs2);
    Scalar qs4 = QuantityScalar.of(RealScalar.of(2), "s", RealScalar.ONE);
    try {
      Sort.of(Tensors.of(qs1, qs4)); // comparison fails
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInterpolation() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Tensor vector = Tensors.of(qs1, qs2, qs3);
    Interpolation interpolation = LinearInterpolation.of(vector);
    Scalar r = QuantityScalar.of(RealScalar.of((1 + 4) * 0.5), "m", RealScalar.ONE);
    Scalar s = interpolation.Get(Tensors.vector(0.5));
    assertEquals(s, r);
  }

  public void testInterpolation2() {
    Tensor v1;
    {
      Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
      Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
      Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
      v1 = Tensors.of(qs1, qs2, qs3);
    }
    Tensor v2;
    {
      Scalar qs1 = QuantityScalar.of(RealScalar.of(9), "s", RealScalar.ONE);
      Scalar qs2 = QuantityScalar.of(RealScalar.of(6), "s", RealScalar.ONE);
      Scalar qs3 = QuantityScalar.of(RealScalar.of(-3), "s", RealScalar.ONE);
      v2 = Tensors.of(qs1, qs2, qs3);
    }
    Tensor matrix = Transpose.of(Tensors.of(v1, v2));
    Interpolation interpolation = LinearInterpolation.of(matrix);
    Scalar r1 = QuantityScalar.of(RealScalar.of((1 + 4) * 0.5), "m", RealScalar.ONE);
    Scalar r2 = QuantityScalar.of(RealScalar.of((9 + 6) * 0.5), "s", RealScalar.ONE);
    Tensor vec = interpolation.get(Tensors.vector(0.5));
    assertEquals(vec, Tensors.of(r1, r2));
  }
}
