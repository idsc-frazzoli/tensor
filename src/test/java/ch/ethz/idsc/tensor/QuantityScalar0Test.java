// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class QuantityScalar0Test extends TestCase {
  private void _checkPlusSymmetry(Scalar s1, Scalar s2) {
    Scalar r1 = s1.add(s2);
    Scalar r2 = s2.add(s1);
    assertEquals(r1.toString(), r2.toString());
  }

  public void testPlusSymmetry() {
    _checkPlusSymmetry( //
        QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE), //
        QuantityScalar.of(RealScalar.of(0), "kg", RealScalar.ONE));
    _checkPlusSymmetry( //
        QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE), //
        QuantityScalar.of(RealScalar.of(2), "kg", RealScalar.ONE));
    _checkPlusSymmetry( //
        QuantityScalar.of(ComplexScalar.of(0, 0), "m", RealScalar.ONE), //
        RealScalar.of(0));
    _checkPlusSymmetry( //
        QuantityScalar.of(ComplexScalar.of(1, 3), "m", RealScalar.ONE), //
        RealScalar.of(0));
    _checkPlusSymmetry( //
        QuantityScalar.of(RealScalar.of(0), "s", RealScalar.ONE), //
        RealScalar.of(0.0));
  }

  public void testPlusUnits1() {
    Scalar s1 = QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE); //
    Scalar s2 = QuantityScalar.of(RealScalar.of(0), "kg", RealScalar.ONE);
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), RealScalar.ZERO.toString());
  }

  public void testPlusUnits2() {
    Scalar s1 = QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE); //
    Scalar s2 = QuantityScalar.of(RealScalar.of(0.0), "kg", RealScalar.ONE);
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), RealScalar.of(0.0).toString());
  }

  public void testPlusUnits3() {
    Scalar s1 = QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE); //
    Scalar s2 = QuantityScalar.of(RealScalar.of(0.0), "m", RealScalar.ONE);
    _checkPlusSymmetry(s1, s2);
    assertEquals(s1.add(s2).toString(), s2.toString()); // result in numeric precision
  }

  public void testPlusFail() {
    try {
      QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE).add( //
          QuantityScalar.of(RealScalar.of(2), "kg", RealScalar.ONE));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      _checkPlusSymmetry( //
          QuantityScalar.of(ComplexScalar.of(1, 2), "m", RealScalar.ONE), //
          QuantityScalar.of(RealScalar.of(2), "kg", RealScalar.ONE));
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
    _checkEquals(QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE), //
        RealScalar.of(2), false);
    _checkEquals(QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE), //
        RealScalar.of(0.0), true);
    _checkEquals(QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE), //
        RealScalar.of(0), true);
    _checkEquals(QuantityScalar.of(RealScalar.of(0), "s", RealScalar.ONE), //
        RealScalar.of(2), false);
  }

  public void testNumberQ() {
    Scalar s1 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE); //
    assertFalse(MachineNumberQ.of(s1));
    assertFalse(ExactNumberQ.of(s1));
    assertFalse(NumberQ.of(s1));
  }
}
