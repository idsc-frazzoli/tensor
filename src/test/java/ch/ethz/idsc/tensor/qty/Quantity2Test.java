// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactNumberQ;
import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Gamma;
import junit.framework.TestCase;

public class Quantity2Test extends TestCase {
  private void _checkPlusSymmetry(Scalar s1, Scalar s2) {
    Scalar r1 = s1.add(s2);
    Scalar r2 = s2.add(s1);
    assertEquals(r1.toString(), r2.toString());
  }

  public void testPlusSymmetry() {
    _checkPlusSymmetry(Quantity.of(0, "[m]"), Quantity.of(0, "[kg]"));
    _checkPlusSymmetry(Quantity.of(0, "[m]"), Quantity.of(2, "[kg]"));
    _checkPlusSymmetry( //
        Quantity.of(ComplexScalar.of(0, 0), "[m]"), //
        RealScalar.of(0));
    _checkPlusSymmetry( //
        Quantity.of(ComplexScalar.of(1, 3), "[m]"), //
        RealScalar.of(0));
    _checkPlusSymmetry(Quantity.of(0, "[s]"), RealScalar.of(0.0));
  }

  public void testPlusUnits1() {
    Scalar s1 = Quantity.of(0, "[m]");
    Scalar s2 = Quantity.of(0, "[kg]");
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), RealScalar.ZERO.toString());
  }

  public void testPlusUnits2() {
    Scalar s1 = Quantity.of(0, "[m]"); //
    Scalar s2 = Quantity.of(0.0, "[kg]");
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), RealScalar.of(0.0).toString());
  }

  public void testPlusUnits3() {
    Scalar s1 = Quantity.of(0, "[m]"); //
    Scalar s2 = Quantity.of(0.0, "[m]");
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), s2.toString()); // result in numeric precision
  }

  public void testPlusFail() {
    try {
      Quantity.of(2, "[m]").add( //
          Quantity.of(2, "[kg]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      _checkPlusSymmetry( //
          Quantity.of(ComplexScalar.of(1, 2), "[m]"), //
          Quantity.of(2, "[kg]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  /***************************************************/
  private void _checkEquals(Scalar s1, Scalar s2, boolean actual) {
    assertEquals(s1.equals(s2), s2.equals(s1));
    assertEquals(s1.equals(s2), actual);
  }

  public void testEquals() {
    _checkEquals(Quantity.of(2, "[m]"), RealScalar.of(2), false);
    _checkEquals(Quantity.of(0, "[m]"), RealScalar.of(0.0), true);
    _checkEquals(Quantity.of(0, "[s]"), RealScalar.of(0), true);
    _checkEquals(Quantity.of(0, "[s*kg^2]"), RealScalar.of(2), false);
  }

  public void testEquals2() {
    _checkEquals(RationalScalar.of(0, 1), Quantity.of(0, "[m]"), true);
    _checkEquals(DoubleScalar.of(0.0), Quantity.of(0, "[m]"), true);
    _checkEquals(DecimalScalar.of(0.0), Quantity.of(0, "[m]"), true);
  }

  public void testNumberQ() {
    Scalar s1 = Quantity.of(3, "[m]"); //
    assertFalse(MachineNumberQ.of(s1));
    assertFalse(ExactNumberQ.of(s1));
    assertFalse(NumberQ.of(s1));
  }

  public void testCompareEquals() {
    Scalar q1 = Quantity.of(0, "[s]");
    Scalar q2 = Quantity.of(0, "[rad]");
    int cmp = Scalars.compare(q1, q2);
    assertEquals(cmp, 0);
    assertTrue(q1.equals(q2));
  }

  public void testGammaFail() {
    try {
      Gamma.of(Quantity.of(3, "[m*s]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(Quantity.of(-2, "[m]")); // <- fails for the wrong reason
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(Quantity.of(-2.12, "[m^2]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testClip() {
    Scalar min = Quantity.of(-3, "[m]");
    Scalar max = Quantity.of(2, "[m]");
    Clip clip = Clip.function(min, max);
    assertEquals(clip.apply(Quantity.of(-5, "[m]")), min);
    assertEquals(clip.apply(Quantity.of(5, "[m]")), max);
    Scalar value = Quantity.of(-1, "[m]");
    assertEquals(clip.apply(value), value);
  }

  public void testClipZero() {
    assertEquals(Clip.function(0, 0).apply(Quantity.of(-5, "[m]")), RealScalar.ZERO);
  }

  public void testClipFail() {
    try {
      Clip.unit().apply(Quantity.of(-5, "[m]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Clip.absoluteOne().apply(Quantity.of(-5, "[m]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  private static boolean _isNonNegative(Scalar scalar) {
    return Scalars.lessEquals(scalar.zero(), scalar);
  }

  public void testPredicate() {
    assertTrue(_isNonNegative(Quantity.of(3, "[m^2]")));
    assertTrue(_isNonNegative(Quantity.of(0, "[s*kg]")));
    assertFalse(_isNonNegative(Quantity.of(-3, "[m]")));
  }
}
