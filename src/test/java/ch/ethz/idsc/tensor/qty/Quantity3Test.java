// code by jph
package ch.ethz.idsc.tensor.qty;

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
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Mod;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Ramp;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class Quantity3Test extends TestCase {
  public void testSimple() {
    Scalar qs1 = Quantity.of(RealScalar.of(2), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(5), "[m]");
    assertTrue(Scalars.isZero(qs1.add(qs2).subtract(qs3)));
  }

  public void testNorm1() {
    Scalar qs1 = Quantity.of(RealScalar.of(-3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(-4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(7), "[m]");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._1.of(vec), qs3);
  }

  public void testNorm2() {
    Scalar qs1 = Quantity.of(RealScalar.of(3), "[m^2]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m^2]");
    Scalar qs3 = Quantity.of(RealScalar.of(5), "[m^2]");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._2.of(vec), qs3);
  }

  public void testNormInf() {
    Scalar qs1 = Quantity.of(RealScalar.of(-3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(-4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(4), "[m]");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm.INFINITY.of(vec), qs3);
  }

  public void testNormInfMixed() {
    Scalar qs1 = Quantity.of(RealScalar.of(-3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor vec = Tensors.of(qs1, RealScalar.ZERO, qs2);
    Scalar nin = Norm.INFINITY.of(vec);
    Scalar act = Quantity.of(RealScalar.of(3), "[m]");
    assertEquals(nin, act);
  }

  public void testSort() {
    Scalar qs1 = Quantity.of(RealScalar.of(-3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor vec = Tensors.of(RealScalar.ZERO, qs2, qs1);
    assertEquals(Sort.of(vec), Tensors.of(qs1, RealScalar.ZERO, qs2));
  }

  public void testChop() {
    Scalar qs1 = Quantity.of(RealScalar.of(1e-9), "[kg]");
    Scalar act = Quantity.of(RealScalar.ZERO, "[kg]");
    assertEquals(Chop._07.of(qs1), act);
    assertEquals(Chop._10.of(qs1), qs1);
  }

  public void testSqrt() {
    Scalar qs1 = Quantity.of(RealScalar.of(9), "[m^2]");
    Scalar qs2 = Quantity.of(RealScalar.of(3), "[m]");
    assertEquals(Sqrt.of(qs1), qs2);
  }

  public void testPower1() {
    Scalar qs1 = Quantity.of(RealScalar.of(9), "[m^2]");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(RealScalar.of(729), "[m^6]");
    assertEquals(res, act);
  }

  public void testPower2() {
    Scalar qs1 = Quantity.of(RealScalar.of(-2), "[m^-3*rad]");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(RealScalar.of(-8), "[m^-9*rad^3]");
    assertEquals(res, act);
  }

  public void testScale() {
    Scalar qs1 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(5), "[m]");
    Tensor vec = Tensors.of(qs1, qs2, qs3);
    Tensor sca = vec.multiply(RealScalar.of(3));
    assertEquals(sca.toString(), "{9[m], 12[m], 15[m]}");
  }

  public void testMultiply() {
    Scalar qs1 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(-2), "[s]");
    assertEquals(qs1.multiply(qs2).toString(), "-6[m*s]");
  }

  public void testDivide() {
    Scalar qs1 = Quantity.of(RealScalar.of(12), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(3), "[m^0]");
    assertEquals(qs1.divide(qs2), qs3);
    assertTrue(qs3 instanceof RationalScalar);
  }

  public void testArcTan() {
    Scalar qs1 = Quantity.of(RealScalar.of(12), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs0 = Quantity.of(RealScalar.of(0), "[m]");
    ArcTan.of(qs1, qs2);
    ArcTan.of(qs0, qs1);
  }

  public void testInvert() {
    Scalar qs1 = Quantity.of(RealScalar.of(4), "[m]");
    assertEquals(qs1.invert().toString(), "1/4[m^-1]");
  }

  public void testRamp() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(-2), "[m]");
    assertEquals(Ramp.of(qs1), qs1);
    assertEquals(Ramp.of(qs2), qs1.zero());
  }

  public void testRound() {
    Scalar qs1 = Quantity.of(RealScalar.of(2.333), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    assertEquals(Round.of(qs1), qs2);
  }

  public void testHypot() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(0), "[m]");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs1);
  }

  public void testHypot2() {
    Scalar qs1 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(5), "[m]");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs3);
  }

  public void testDiagon() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[s]");
    Tensor vec = Tensors.of(qs1, qs2);
    DiagonalMatrix.of(vec);
    // System.out.println(Pretty.of(mat));
  }

  public void testMod() {
    Scalar qs1 = Quantity.of(RealScalar.of(5), "[s]");
    Scalar qs2 = Quantity.of(RealScalar.of(3), "[s]");
    Scalar qs3 = Quantity.of(RealScalar.of(2), "[s]");
    Scalar res = Mod.function(qs2).apply(qs1);
    // System.out.println(res);
    assertEquals(res, qs3);
  }

  public void testCopySign1() {
    Scalar qs1 = Quantity.of(RealScalar.of(5), "[s]");
    Scalar qs2 = Quantity.of(RealScalar.of(-3), "[m]");
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }

  public void testCopySign2() {
    Scalar qs1 = Quantity.of(RealScalar.of(5), "[s]");
    Scalar qs2 = RealScalar.of(-3);
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }
}
