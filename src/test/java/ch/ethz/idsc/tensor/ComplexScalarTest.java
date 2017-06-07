// code by jph
package ch.ethz.idsc.tensor;

import java.util.Random;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class ComplexScalarTest extends TestCase {
  public void testAbs() {
    ComplexScalar s = (ComplexScalar) ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    // ----------------------------------------- 0.668539037337719303091638399542
    Scalar a = s.abs(); // --------------------- 0.6685390373377194
    Scalar c = RationalScalar.of(1609, 3600); // 0.6685390373377192
    Tensor r = Sqrt.of(c);
    double d = Math.sqrt(c.number().doubleValue());
    assertEquals(Chop.of(a.subtract(r)), RealScalar.ZERO);
    String prefix = "0.668539037337719";
    assertTrue(a.toString().startsWith(prefix));
    assertTrue(r.toString().startsWith(prefix));
    assertTrue((d + "").startsWith(prefix));
  }

  public void testAbs2() {
    Scalar s = ComplexScalar.of(RealScalar.of(-3), RealScalar.of(4));
    assertTrue(s.abs() instanceof RationalScalar);
  }

  public void testFalseConstruct() {
    Scalar c1 = ComplexScalar.of(3, -4);
    Scalar c2 = ComplexScalar.of(-2, 9);
    try {
      ComplexScalar.of(c1, c2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMultiply() {
    Scalar c = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    assertEquals(Real.of(c), RealScalar.of(2));
    assertEquals(Imag.of(c), RationalScalar.of(5, 8));
    assertEquals(Abs.of(c), Norm._2.of(Tensors.of(RealScalar.of(2), RationalScalar.of(5, 8))));
    Scalar r = RealScalar.of(-6);
    assertEquals(Real.of(r), r);
    assertEquals(Imag.of(r), RealScalar.ZERO);
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
    Scalar c2 = ComplexScalar.of(RealScalar.ZERO, RationalScalar.of(5, 8));
    Scalar c3 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(-5, 8));
    Scalar c4 = ComplexScalar.of(RealScalar.ZERO, RationalScalar.of(-5, 8));
    assertEquals("2+5/8*I", c1.toString());
    assertEquals("5/8*I", c2.toString());
    assertEquals("2-5/8*I", c3.toString());
    assertEquals("-5/8*I", c4.toString());
    assertEquals(c1, Scalars.fromString(c1.toString()));
    assertEquals(c2, Scalars.fromString(c2.toString()));
    assertEquals(c3, Scalars.fromString(c3.toString()));
    assertEquals(c4, Scalars.fromString(c4.toString()));
    assertEquals(c2, Scalars.fromString("5/8*I"));
    assertEquals(c4, Scalars.fromString("-5/8*I"));
  }

  public void testEquals() {
    Scalar c1 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar c2 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar s1 = RationalScalar.of(3, 2);
    assertFalse(c1.equals(s1));
    assertFalse(s1.equals(c1));
    assertEquals(c1.hashCode(), c2.hashCode());
  }

  public void testSerializable() throws Exception {
    Scalar a = ComplexScalar.of(3, 5.2345);
    assertEquals(a, Serialization.parse(Serialization.of(a)));
  }

  public void testConjugate() {
    Scalar s = ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    assertEquals(AbsSquared.of(s), RationalScalar.of(1609, 3600));
    ComplexScalar c = (ComplexScalar) ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar ra = RationalScalar.of(5, 8);
    assertEquals(Conjugate.of(ra), RationalScalar.of(5, 8));
    Scalar s1 = c.conjugate();
    Scalar s2 = ComplexScalar.of(RealScalar.of(2), ra.negate());
    assertEquals(s1.toString(), s2.toString());
    assertEquals(s1, s2);
    assertEquals(c.conjugate(), s2);
    assertEquals(s2, c.conjugate());
    assertEquals(c.imag(), ra);
  }

  public void testPower() {
    Scalar s = ComplexScalar.I;
    Scalar r = Power.of(s, 3);
    assertEquals(r, ComplexScalar.I.negate());
  }

  public void testFloor() {
    Scalar s = ComplexScalar.of(121.3, -111.1);
    Scalar r = ComplexScalar.of(121, -112);
    assertEquals(Floor.of(s), r);
  }

  public void testRound() {
    Scalar s = ComplexScalar.of(12.3, -3.9);
    Scalar r = ComplexScalar.of(12, -4);
    assertEquals(Round.of(s), r);
  }

  public void testPower2() {
    Scalar s = ComplexScalar.of(RationalScalar.of(2, 7), RationalScalar.of(-4, 3));
    assertEquals(Power.of(s, -3), Scalars.fromString("-16086357/68921000-10955763/34460500*I"));
  }

  public void testToString() {
    // Scalar c1 = ComplexScalar.of(RealScalar.of(2), RationalScalar.of(5, 8));
    Scalar c2 = ComplexScalar.of(RealScalar.ZERO, RationalScalar.of(5, 8));
    // System.out.println(c2);
    assertEquals(c2.toString(), "5/8*I");
  }

  public void testTensor() {
    Tensor u = Tensors.fromString("{I,3/4-5*I}");
    Tensor uc = Conjugate.of(u);
    // System.out.println(uc.toString());
    assertEquals(uc.toString(), "{-I, 3/4+5*I}");
  }
}
