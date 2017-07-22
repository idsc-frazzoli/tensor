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
    Scalar s1 = Quantity.of(RealScalar.of(0), "[m]"); //
    Scalar s2 = Quantity.of(RealScalar.of(0.0), "[kg]");
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), RealScalar.of(0.0).toString());
  }

  public void testPlusUnits3() {
    Scalar s1 = Quantity.of(RealScalar.of(0), "[m]"); //
    Scalar s2 = Quantity.of(RealScalar.of(0.0), "[m]");
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), s2.toString()); // result in numeric precision
  }

  public void testPlusFail() {
    try {
      Quantity.of(RealScalar.of(2), "[m]").add( //
          Quantity.of(RealScalar.of(2), "[kg]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      _checkPlusSymmetry( //
          Quantity.of(ComplexScalar.of(1, 2), "[m]"), //
          Quantity.of(RealScalar.of(2), "[kg]"));
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
    _checkEquals(Quantity.of(RealScalar.of(2), "[m]"), //
        RealScalar.of(2), false);
    _checkEquals(Quantity.of(RealScalar.of(0), "[m]"), //
        RealScalar.of(0.0), true);
    _checkEquals(Quantity.of(RealScalar.of(0), "[s]"), //
        RealScalar.of(0), true);
    _checkEquals(Quantity.of(RealScalar.of(0), "[s*kg^2]"), //
        RealScalar.of(2), false);
  }

  public void testEquals2() {
    _checkEquals(RationalScalar.of(0, 1), Quantity.of(0, "[m]"), true);
    _checkEquals(DoubleScalar.of(0.0), Quantity.of(0, "[m]"), true);
    _checkEquals(DecimalScalar.of(0.0), Quantity.of(0, "[m]"), true);
  }

  public void testNumberQ() {
    Scalar s1 = Quantity.of(RealScalar.of(3), "[m]"); //
    assertFalse(MachineNumberQ.of(s1));
    assertFalse(ExactNumberQ.of(s1));
    assertFalse(NumberQ.of(s1));
  }
}
