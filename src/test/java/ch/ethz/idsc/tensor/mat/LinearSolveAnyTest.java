// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class LinearSolveAnyTest extends TestCase {
  public void testSome1() {
    Tensor m = Tensors.fromString("{{1, 2, 3}, {5, 6, 7}, {7, 8, 9}}");
    Tensor b = Tensors.fromString("{1, 1, 1}");
    Tensor x = LinearSolve.any(m, b);
    assertEquals(x, Tensors.fromString("{-1, 1, 0}"));
  }

  public void testDiag() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor x = LinearSolve.any(DiagonalMatrix.with(vector), vector);
    assertEquals(x, Tensors.fromString("{1, 1, 0, 1, 1, 1}"));
  }

  public void testDiag2() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(DiagonalMatrix.with(vector), Array.zeros(3, 6));
    Tensor b = Join.of(vector, Array.zeros(3));
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testDiag2b() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    // m is 6 x 9 matrix
    Tensor m = Join.of(1, DiagonalMatrix.with(vector), Array.zeros(6, 3));
    Tensor b = Join.of(vector);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
    assertEquals(Det.of(m), RealScalar.ZERO);
  }

  public void testDiag3() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(Array.zeros(3, 6), DiagonalMatrix.with(vector));
    Tensor b = Join.of(Array.zeros(3), vector);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
  }

  public void testDiag3b() {
    Tensor vector = Tensors.vector(3, 2, 0, 5, 4, 7);
    Tensor m = Join.of(1, Array.zeros(6, 3), DiagonalMatrix.with(vector));
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

  public void testAny2() {
    Tensor m = Tensors.fromString("{{1}, {1}, {-1}}");
    Tensor b = Tensors.vector(2, 2, -2);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
    assertEquals(Det.of(m), RealScalar.ZERO);
  }

  public void testAnyN() {
    Tensor m = Tensors.fromString("{{1}, {1}, {-5}}");
    Tensor b = Tensors.vector(-2, -2, 10);
    Tensor x = LinearSolve.any(m, b);
    assertEquals(m.dot(x), b);
    assertEquals(Det.of(m), RealScalar.ZERO);
  }

  @SuppressWarnings("unused")
  public void testLarge() {
    Distribution distribution = NormalDistribution.standard();
    Tensor m = RandomVariate.of(distribution, 2, 4);
    Tensor x = RandomVariate.of(distribution, 4);
    Tensor b = m.dot(x);
    // Tensor s = LinearSolve.any(m, b);
  }
}
