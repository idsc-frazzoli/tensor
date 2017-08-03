// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import junit.framework.TestCase;

public class McScalarTest extends TestCase {
  public void testPlus() {
    Scalar s1 = McScalar.of(RealScalar.of(2), RealScalar.of(4));
    Scalar s2 = McScalar.of(RationalScalar.of(2, 9), RealScalar.of(-3));
    assertEquals(s1.add(s2), s2.add(s1));
    Scalar r = RealScalar.of(5);
    assertEquals(s1.add(r), r.add(s1));
  }

  public void testMultiply() {
    Scalar s1 = McScalar.of(RealScalar.of(2), RealScalar.of(4));
    Scalar s2 = McScalar.of(RationalScalar.of(2, 9), RealScalar.of(-3));
    assertEquals(s1.multiply(s2), s2.multiply(s1));
    Scalar r = RealScalar.of(5);
    assertEquals(s1.multiply(r), r.multiply(s1));
  }

  public void testReciprocal() {
    Scalar s1 = McScalar.of(RationalScalar.of(2, 9), RealScalar.of(-3));
    Scalar s3 = s1.reciprocal();
    assertEquals(s3.multiply(s1), RealScalar.ONE);
  }

  public void testLinearSolve() {
    Scalar s1 = McScalar.of(RealScalar.of(2), RealScalar.of(4));
    Scalar s2 = McScalar.of(RationalScalar.of(2, 9), RealScalar.of(-3));
    Scalar s3 = McScalar.of(RealScalar.of(-2), RealScalar.of(10));
    Scalar s4 = McScalar.of(RationalScalar.of(8, 7), RealScalar.of(-1));
    Tensor A = Tensors.matrix(new Scalar[][] { { s1, s2 }, { s3, s4 } });
    Tensor B = Inverse.withoutAbs(A);
    assertEquals(A.dot(B), IdentityMatrix.of(2));
  }

  public void testLinearSolve2() {
    Scalar s1 = McScalar.of(RealScalar.of(2), RealScalar.of(4));
    Scalar s2 = McScalar.of(RationalScalar.of(2, 9), RealScalar.of(-3));
    Scalar s3 = McScalar.of(RealScalar.of(-2), RealScalar.of(10));
    Scalar s4 = McScalar.of(RationalScalar.of(8, 7), RealScalar.of(-1));
    Tensor A = Tensors.matrix(new Scalar[][] { { s1, s2 }, { s3, s4 } });
    Tensor B = Inverse.of(A);
    assertEquals(A.dot(B), IdentityMatrix.of(2));
  }
}
