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
import ch.ethz.idsc.tensor.alg.Join;
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

  public void testSome1() {
    Tensor m = Tensors.fromString("{{1, 2, 3}, {5, 6, 7}, {7, 8, 9}}");
    Tensor b = Tensors.fromString("{1, 1, 1}");
    Tensor x = LinearSolve.any(m, b);
    assertEquals(x, Tensors.fromString("{-1, 1, 0}"));
  }

  public void testDiag() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor x = LinearSolve.any(DiagonalMatrix.of(vector), vector);
    assertEquals(x, Tensors.fromString("{1, 1, 0, 1, 1, 1}"));
  }

  public void testDiag2() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(DiagonalMatrix.of(vector), Array.zeros(3, 6));
    Tensor b = Join.of(vector, Array.zeros(3));
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testDiag2b() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(1, DiagonalMatrix.of(vector), Array.zeros(6, 3));
    Tensor b = Join.of(vector);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testDiag3() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(Array.zeros(3, 6), DiagonalMatrix.of(vector));
    Tensor b = Join.of(Array.zeros(3), vector);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testDiag3b() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(1, Array.zeros(6, 3), DiagonalMatrix.of(vector));
    Tensor b = Join.of(vector);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testSome2() {
    Tensor m = Tensors.fromString("{{1, 2, 3}, {5, 6, 7}, {7, 8, 9}}");
    Tensor b = Tensors.fromString("{1, -2, 1}");
    try {
      LinearSolve.any(m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAny() {
    Tensor m = Tensors.fromString("{{1, 0, -1}, {0,1,0}, {1,0,-1}}");
    Tensor b = Tensors.fromString("{0,0,0}");
    Tensor x = LinearSolve.any(m, b);
    Scalar det = Det.of(m);
    assertEquals(det, RealScalar.ZERO);
    assertEquals(x, b);
  }
}
