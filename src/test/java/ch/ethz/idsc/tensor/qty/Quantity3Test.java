// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.red.CopySign;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.VectorNorm;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Mod;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Ramp;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class Quantity3Test extends TestCase {
  public void testSimple() {
    Scalar qs1 = Quantity.of(2, "[m]");
    Scalar qs2 = Quantity.of(3, "[m]");
    Scalar qs3 = Quantity.of(5, "[m]");
    assertTrue(Scalars.isZero(qs1.add(qs2).subtract(qs3)));
  }

  public void testNorm1() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(-4, "[m]");
    Scalar qs3 = Quantity.of(7, "[m]");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._1.ofVector(vec), qs3);
  }

  public void testNorm1b() {
    Tensor vec = Tensors.of( //
        Quantity.of(-3, "[m]"), //
        Quantity.of(0, "[s*rad]"), //
        RealScalar.ZERO, //
        Quantity.of(-4, "[m]") //
    );
    assertEquals(Norm._1.ofVector(vec), Quantity.of(7, "[m]"));
  }

  public void testNorm2() {
    Tensor vec = Tensors.of( //
        Quantity.of(3, "[m^2]"), //
        Quantity.of(0, "[s*rad]"), //
        Quantity.of(-4, "[m^2]"), //
        RealScalar.ZERO //
    );
    assertEquals(Norm._2.of(vec), Quantity.of(5, "[m^2]"));
  }

  public void testNorm2b() {
    Tensor vec = Tensors.fromString("{0[m^2],0[s*rad],1}", Quantity::fromString);
    assertEquals(Norm._2.of(vec), RealScalar.ONE);
  }

  public void testNormInf() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(-4, "[m]");
    Scalar qs3 = Quantity.of(4, "[m]");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm.INFINITY.of(vec), qs3);
  }

  public void testNormInfMixed() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(2, "[m]");
    Tensor vec = Tensors.of(qs1, RealScalar.ZERO, qs2);
    Scalar nin = Norm.INFINITY.of(vec);
    Scalar act = Quantity.of(3, "[m]");
    assertEquals(nin, act);
  }

  public void testNormP() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    Tensor vec = Tensors.of(qs1, RealScalar.ZERO, qs2);
    Scalar lhs = VectorNorm.with(RationalScalar.of(7, 3)).ofVector(vec);
    Scalar rhs = Quantity.fromString("4.774145448367236[m]");
    assertTrue(Chop._13.close(lhs, rhs));
  }

  public void testNormP2() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    Tensor vec = Tensors.of(qs1, RealScalar.ZERO, qs2);
    Scalar lhs = VectorNorm.with(Math.PI).ofVector(vec); // the result has unit [m^1.0]
    Scalar rhs = Quantity.fromString("4.457284396597481[m]");
    assertTrue(Chop._13.close(lhs, rhs));
  }

  public void testSort() {
    Scalar qs1 = Quantity.of(-3, "[m]");
    Scalar qs2 = Quantity.of(2, "[m]");
    Tensor vec = Tensors.of(RealScalar.ZERO, qs2, qs1);
    assertEquals(Sort.of(vec), Tensors.of(qs1, RealScalar.ZERO, qs2));
  }

  public void testChop() {
    Scalar qs1 = Quantity.of(1e-9, "[kg]");
    Scalar act = Quantity.of(0, "[kg]");
    assertEquals(Chop._07.of(qs1), act);
    assertEquals(Chop._10.of(qs1), qs1);
  }

  public void testSqrt() {
    Scalar qs1 = Quantity.of(9, "[m^2]");
    Scalar qs2 = Quantity.of(3, "[m]");
    assertEquals(Sqrt.of(qs1), qs2);
  }

  public void testPower1() {
    Scalar qs1 = Quantity.of(9, "[m^2]");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(729, "[m^6]");
    assertEquals(res, act);
  }

  public void testPower2() {
    Scalar qs1 = Quantity.of(-2, "[m^-3*rad]");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(-8, "[m^-9*rad^3]");
    assertEquals(res, act);
  }

  public void testScale() {
    Scalar qs1 = Quantity.of(3, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    Scalar qs3 = Quantity.of(5, "[m]");
    Tensor vec = Tensors.of(qs1, qs2, qs3);
    Tensor sca = vec.multiply(RealScalar.of(3));
    assertEquals(sca.toString(), "{9[m], 12[m], 15[m]}");
  }

  public void testMultiply() {
    Scalar qs1 = Quantity.of(3, "[m]");
    Scalar qs2 = Quantity.of(-2, "[s]");
    assertEquals(qs1.multiply(qs2).toString(), "-6[m*s]");
  }

  public void testDivide() {
    Scalar qs1 = Quantity.of(12, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    Scalar qs3 = Quantity.of(3, "[m^0]");
    assertEquals(qs1.divide(qs2), qs3);
    assertTrue(qs3 instanceof RationalScalar);
  }

  public void testAbsSquared() {
    Scalar qs1 = Quantity.fromString("3+4*I[s^2*m^-1]");
    Scalar qs2 = AbsSquared.FUNCTION.apply(qs1);
    assertEquals(qs2.toString(), "25[m^-2*s^4]");
  }

  // Mathematica doesn't do this:
  // ArcTan[Quantity[12, "Meters"], Quantity[4, "Meters"]] is not evaluated
  public void testArcTan() {
    Scalar qs1 = Quantity.of(12, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    {
      assertFalse(qs1 instanceof RealScalar);
      Scalar res = ArcTan.of(qs1, qs2);
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.close(res, RealScalar.of(0.32175055439664219340)));
    }
  }

  public void testArcTanZeroX() {
    Scalar qs0 = Quantity.of(0, "[s]");
    Scalar qs1 = Quantity.of(12, "[m]");
    {
      Scalar res = ArcTan.of(RealScalar.ZERO, qs1);
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.close(res, RealScalar.of(Math.PI / 2)));
    }
    {
      Scalar res = ArcTan.of(RealScalar.ZERO, qs0);
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.allZero(res));
    }
    {
      Scalar res = ArcTan.of(qs0, qs1);
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.close(res, RealScalar.of(Math.PI / 2)));
    }
    {
      Scalar res = ArcTan.of(qs0, RealScalar.of(12));
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.close(res, RealScalar.of(Math.PI / 2)));
    }
  }

  public void testArcTanZeroY() {
    Scalar qs0 = Quantity.of(0, "[s]");
    Scalar qs1 = Quantity.of(12, "[m]");
    {
      Scalar res = ArcTan.of(qs1, qs0);
      assertTrue(res instanceof RealScalar);
      assertTrue(Chop._10.allZero(res));
    }
  }

  public void testArcTanFail() {
    try {
      ArcTan.of(Quantity.of(12, "[m]"), Quantity.of(4, "[s]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ArcTan.of(Quantity.of(12, "[m]"), RealScalar.of(4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ArcTan.of(RealScalar.of(12), Quantity.of(4, "[s]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testReciprocal() {
    Scalar qs1 = Quantity.of(4, "[m]");
    assertEquals(qs1.reciprocal().toString(), "1/4[m^-1]");
  }

  public void testRamp() {
    Scalar qs1 = Quantity.of(1, "[m]");
    Scalar qs2 = Quantity.of(-2, "[m]");
    assertEquals(Ramp.of(qs1), qs1);
    assertEquals(Ramp.of(qs2), qs1.zero());
  }

  public void testRound() {
    Scalar qs1 = Quantity.of(2.333, "[m]");
    Scalar qs2 = Quantity.of(2, "[m]");
    assertEquals(Round.of(qs1), qs2);
  }

  public void testHypot() {
    Scalar qs1 = Quantity.of(1, "[m]");
    Scalar qs2 = Quantity.of(0, "[m]");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs1);
  }

  public void testHypot2() {
    Scalar qs1 = Quantity.of(3, "[m]");
    Scalar qs2 = Quantity.of(4, "[m]");
    Scalar qs3 = Quantity.of(5, "[m]");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs3);
  }

  public void testDiagon() {
    Scalar qs1 = Quantity.of(1, "[m]");
    Scalar qs2 = Quantity.of(2, "[s]");
    Tensor vec = Tensors.of(qs1, qs2);
    DiagonalMatrix.of(vec);
  }

  public void testMod() {
    Scalar qs1 = Quantity.of(5, "[s]");
    Scalar qs2 = Quantity.of(3, "[s]");
    Scalar qs3 = Quantity.of(2, "[s]");
    Scalar res = Mod.function(qs2).apply(qs1);
    assertEquals(res, qs3);
  }

  public void testCopySign1() {
    Scalar qs1 = Quantity.of(5, "[s]");
    Scalar qs2 = Quantity.of(-3, "[m]");
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }

  public void testCopySign2() {
    Scalar qs1 = Quantity.of(5, "[s]");
    Scalar qs2 = RealScalar.of(-3);
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }

  public void testComplex0() {
    Scalar s = Quantity.fromString("0+0*I[m*s]");
    assertTrue(s instanceof Quantity);
    assertEquals(Real.of(s).toString(), "0[m*s]");
    assertEquals(Imag.of(s).toString(), "0[m*s]");
    assertEquals(Conjugate.of(s).toString(), "0[m*s]");
  }

  public void testComplex1() {
    Scalar s = Quantity.fromString("3+5*I[m*s]");
    assertTrue(s instanceof Quantity);
    assertEquals(Real.of(s), Quantity.fromString("3[m*s]"));
    assertEquals(Imag.of(s), Quantity.fromString("5[m*s]"));
    assertEquals(Conjugate.of(s), Quantity.fromString("3-5*I[m*s]"));
  }

  public void testComplex2() {
    Scalar s1 = ComplexScalar.of(1, 2);
    Scalar s2 = Quantity.fromString("0[m*s]");
    assertEquals(s1, s1.add(s2));
  }
}
