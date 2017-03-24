// code by jph
package ch.ethz.idsc.tensor;

import java.util.Random;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Real;
import junit.framework.TestCase;

public class ComplexScalarTest extends TestCase {
  public void testAbs() {
    ComplexScalar s = (ComplexScalar) ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    assertEquals(s.abs(), RealScalar.of(Math.sqrt(1609. / 3600)));
  }

  public void testMultiply() {
    Scalar c = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    assertEquals(Real.of(c), RealScalar.of(2));
    assertEquals(Imag.of(c), RationalScalar.of(5, 8));
    assertEquals(Abs.of(c), Norm._2.of(Tensors.of(RealScalar.of(2), RationalScalar.of(5, 8))));
    Scalar r = RealScalar.of(-6);
    assertEquals(Real.of(r), r);
    assertEquals(Imag.of(r), ZeroScalar.get());
    Scalar r2 = RealScalar.of(6);
    assertEquals(r.multiply(c).toString(), "-12-15/4*I");
    assertEquals(c.multiply(r).toString(), "-12-15/4*I");
    assertEquals(r2.multiply(c).toString(), "12+15/4*I");
    assertEquals(c.multiply(r2).toString(), "12+15/4*I");
  }

  public void testSolveCR() {
    int n = 8;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    ComplexScalar.of( //
        RealScalar.of(random.nextInt(15)), //
        RealScalar.of(random.nextInt(15))), n, n);
    Tensor b = Tensors.matrix((i, j) -> ComplexScalar.of(//
        random.nextInt(15), //
        random.nextInt(15)), n, n + 3);
    Tensor X = LinearSolve.of(A, b);
    Tensor err = A.dot(X).subtract(b);
    assertEquals(A.dot(X), b);
    assertEquals(err, Array.zeros(Dimensions.of(b)));
  }

  public void testParsing() {
    Scalar c1 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar c2 = ComplexScalar.of(ZeroScalar.get(), RationalScalar.of(5, 8));
    Scalar c3 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(-5, 8));
    Scalar c4 = ComplexScalar.of(ZeroScalar.get(), RationalScalar.of(-5, 8));
    assertEquals(c1, Scalars.fromString(c1.toString()));
    assertEquals(c2, Scalars.fromString(c2.toString()));
    assertEquals(c3, Scalars.fromString(c3.toString()));
    assertEquals(c4, Scalars.fromString(c4.toString()));
    assertEquals(c2, Scalars.fromString("0+5/8*I"));
    assertEquals(c4, Scalars.fromString("0-5/8*I"));
  }

  public void testEquals() {
    Scalar c1 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar c2 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar s1 = RationalScalar.of(3, 2);
    assertFalse(c1.equals(s1));
    assertFalse(s1.equals(c1));
    assertEquals(c1.hashCode(), c2.hashCode());
  }

  public void testConjugate() {
    Scalar s = ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    assertEquals(s.absSquared(), RationalScalar.of(1609, 3600));
    ComplexScalar c = (ComplexScalar) ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar ra = RationalScalar.of(5, 8);
    assertEquals(ra.conjugate(), RationalScalar.of(5, 8));
    Scalar s1 = c.conjugate();
    Scalar s2 = ComplexScalar.of(RealScalar.of(2), ra.negate());
    assertEquals(s1.toString(), s2.toString());
    assertEquals(s1, s2);
    assertEquals(c.conjugate(), s2);
    assertEquals(s2, c.conjugate());
    assertEquals(c.imag(), ra);
  }

  public void testTensor() {
    Tensor u = Tensors.fromString("[0+1*I,3/4-5*I]");
    Tensor uc = u.conjugate();
    assertEquals(uc.toString(), "[0-1*I, 3/4+5*I]");
  }
}
