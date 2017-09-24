// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class LinearSolveTest extends TestCase {
  public void testSolveCR() {
    int n = 5;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    ComplexScalar.of( //
        RealScalar.of(random.nextInt(15)), //
        RealScalar.of(random.nextInt(15))), n, n);
    Tensor b = Tensors.matrix((i, j) -> RationalScalar.of(i.equals(j) ? 1 : 0, 1), n, n + 3);
    Tensor X = LinearSolve.of(A, b);
    Tensor err = A.dot(X).subtract(b);
    assertEquals(err, b.multiply(RealScalar.ZERO));
    assertEquals(err, Array.zeros(Dimensions.of(b)));
  }

  public void testSolveRC() {
    int n = 10;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    RationalScalar.of(random.nextInt(100), random.nextInt(100) + 1), n, n);
    Tensor b = Tensors.matrix((i, j) -> ComplexScalar.of(//
        RealScalar.of(random.nextInt(15)), //
        RealScalar.of(random.nextInt(15))), n, n + 3);
    Tensor X = LinearSolve.of(A, b);
    Tensor err = A.dot(X).subtract(b);
    assertEquals(err, b.multiply(RealScalar.ZERO));
    assertEquals(err, Array.zeros(Dimensions.of(b)));
  }

  public void testSolveDC() {
    int n = 15;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(4 * random.nextGaussian() - 2), n, n);
    Tensor b = Tensors.matrix((i, j) -> ComplexScalar.of( //
        RealScalar.of(random.nextDouble()), //
        RealScalar.of(random.nextDouble())), n, n + 3);
    Tensor X = LinearSolve.of(A, b);
    Tensor err = A.dot(X).add(b.negate()).map(Chop._10);
    assertEquals(err, b.multiply(RealScalar.ZERO));
    assertEquals(err, Array.zeros(Dimensions.of(b)));
  }

  public void testGauss() {
    Tensor vec1 = Tensors.vectorDouble(0, 2, 5.3);
    Tensor vec2 = Tensors.vectorDouble(-1.0, 3.1, .3);
    Tensor vec3 = Tensors.vectorDouble(2.0, 0.4, .3);
    Tensor b = Tensors.vectorDouble(.3, .5, .7);
    Tensor A = Tensors.of(vec1, vec2, vec3);
    {
      Tensor x = LinearSolve.of(A, b);
      Tensor err = A.dot(x).add(b.negate());
      assertEquals(Chop._12.of(err), Tensors.vectorLong(0, 0, 0));
      assertEquals(Chop._12.of(err), Array.zeros(3));
    }
    Tensor eye2 = Tensors.of( //
        Tensors.vectorDouble(1.0, 0.0, 0.0, 3), //
        Tensors.vectorDouble(0.0, 0.0, 1.0, 5));
    Tensor eye3 = Tensors.of(eye2, eye2, eye2);
    Tensor sol = LinearSolve.of(A, eye3);
    {
      Tensor err = A.dot(sol).add(eye3.negate());
      assertEquals(Chop._12.of(err), eye3.multiply(DoubleScalar.of(0)));
      assertEquals(Chop._12.of(err), Array.zeros(Dimensions.of(eye3)));
    }
  }

  public void testIdentity() {
    int n = 5;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    RationalScalar.of(random.nextInt(100) - 50, random.nextInt(100) + 1), n, n);
    Tensor b = IdentityMatrix.of(n);
    Tensor X = LinearSolve.of(A, b);
    assertEquals(X.dot(A), b);
    assertEquals(A.dot(X), b);
  }

  public void testEmpty() {
    try {
      Tensor m = Tensors.matrix(new Number[][] { {} });
      Tensor b = Tensors.vector(new Number[] {});
      LinearSolve.of(m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEps() {
    Tensor m = Tensors.matrixDouble(new double[][] { { Double.MIN_VALUE } });
    Tensor b = Tensors.vectorDouble(new double[] { Double.MIN_VALUE });
    Tensor r = LinearSolve.of(m, b);
    assertEquals(r, Tensors.vector(1));
    assertEquals(Det.of(m), DoubleScalar.of(Double.MIN_VALUE));
  }

  public void testQuantity1() {
    final Scalar one = Quantity.of(1, "m");
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Scalar qs4 = Quantity.of(-3, "m");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2, one);
    Tensor inv = LinearSolve.of(mat, eye);
    Tensor res = mat.dot(inv);
    assertEquals(res, eye);
  }

  public void testQuantity2() {
    Scalar qs1 = Quantity.of(3, "m");
    Scalar qs2 = Quantity.of(4, "s");
    Tensor ve1 = Tensors.of(qs1);
    Tensor mat = Tensors.of(ve1);
    Tensor rhs = Tensors.of(qs2);
    Tensor sol = LinearSolve.of(mat, rhs);
    Tensor res = mat.dot(sol);
    assertEquals(res, rhs);
  }
}
