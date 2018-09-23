// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import junit.framework.TestCase;

public class SignTest extends TestCase {
  public void testSome() {
    assertEquals(Sign.of(DoubleScalar.of(-345)), RealScalar.of(-1));
    assertEquals(Sign.of(DoubleScalar.of(9324.5)), RealScalar.of(1));
    assertEquals(Sign.of(RealScalar.ZERO), RealScalar.ZERO);
  }

  public void testDiff() {
    assertEquals(Sign.of(RealScalar.of(3 - 9)), RealScalar.ONE.negate());
    assertEquals(Sign.of(RealScalar.of(3 - 3)), RealScalar.ZERO);
    assertEquals(Sign.of(RealScalar.of(9 - 3)), RealScalar.ONE);
  }

  public void testDoubleNegZero() {
    Scalar d1 = DoubleScalar.of(-0.0);
    Sign.requirePositiveOrZero(d1);
    assertEquals(d1.toString(), "-0.0");
    Scalar s1 = Sign.of(d1);
    assertEquals(s1.toString(), "0");
    assertEquals(d1, d1.zero());
  }

  public void testInfinity() {
    assertEquals(Sign.of(DoubleScalar.POSITIVE_INFINITY), RealScalar.of(+1));
    assertEquals(Sign.of(DoubleScalar.NEGATIVE_INFINITY), RealScalar.of(-1));
    assertTrue(Sign.isPositive(DoubleScalar.POSITIVE_INFINITY));
    assertTrue(Sign.isNegative(DoubleScalar.NEGATIVE_INFINITY));
  }

  public void testIsNegative() {
    Unit apples = Unit.of("Apples");
    assertTrue(Sign.isNegative(Quantity.of(-2, apples)));
    assertFalse(Sign.isNegative(Quantity.of(0, apples)));
    assertFalse(Sign.isNegative(Quantity.of(2, apples)));
  }

  public void testIsPositive() {
    assertFalse(Sign.isPositive(Quantity.of(-2, "V*A")));
    assertFalse(Sign.isPositive(Quantity.of(0, "V*A")));
    assertTrue(Sign.isPositive(Quantity.of(2, "V*A")));
    assertFalse(Sign.isPositive(Quantity.of(-2, Unit.ONE)));
    assertFalse(Sign.isPositive(Quantity.of(0, Unit.ONE)));
    assertTrue(Sign.isPositive(Quantity.of(2, Unit.ONE)));
  }

  public void testIsPositiveOrZero() {
    Unit apples = Unit.of("Apples");
    assertFalse(Sign.isPositiveOrZero(Quantity.of(-2, apples)));
    assertTrue(Sign.isPositiveOrZero(Quantity.of(0, apples)));
    assertTrue(Sign.isPositiveOrZero(Quantity.of(2, apples)));
  }

  public void testIsNegativeOrZero() {
    assertTrue(Sign.isNegativeOrZero(Quantity.of(-2, "V*A")));
    assertTrue(Sign.isNegativeOrZero(Quantity.of(0, "V*A")));
    assertFalse(Sign.isNegativeOrZero(Quantity.of(2, "V*A")));
    assertTrue(Sign.isNegativeOrZero(Quantity.of(-2, Unit.ONE)));
    assertTrue(Sign.isNegativeOrZero(Quantity.of(0, Unit.ONE)));
    assertFalse(Sign.isNegativeOrZero(Quantity.of(2, Unit.ONE)));
  }

  public void testRequireNonNegative() {
    Sign.requirePositiveOrZero(RealScalar.ZERO);
    Sign.requirePositiveOrZero(RealScalar.ONE);
    Sign.requirePositiveOrZero(Quantity.of(2, "m*s^-2"));
    try {
      Sign.requirePositiveOrZero(RealScalar.ONE.negate());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sign.requirePositiveOrZero(DoubleScalar.INDETERMINATE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRequirePositive() {
    Sign.requirePositive(RealScalar.ONE);
    Sign.requirePositive(Quantity.of(2, "m*s^-2"));
    try {
      Sign.requirePositive(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sign.requirePositive(RealScalar.ONE.negate());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sign.requirePositive(DoubleScalar.INDETERMINATE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  private static void _checkFail(Scalar value) {
    try {
      Sign.of(value);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sign.isPositive(value);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sign.isNegative(value);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    _checkFail(DoubleScalar.INDETERMINATE);
    _checkFail(ComplexScalar.of(2, 3));
    _checkFail(StringScalar.of("string"));
  }
}
