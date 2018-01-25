// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantityCompareTest extends TestCase {
  private static void _checkEquals(Scalar s1, Scalar s2, boolean actual) {
    assertEquals(s1.equals(s2), s2.equals(s1));
    assertEquals(s1.equals(s2), actual);
  }

  public void testEquals() {
    _checkEquals(Quantity.of(2, "m"), RealScalar.of(2), false);
    _checkEquals(Quantity.of(0, "m"), RealScalar.of(0.0), false);
    _checkEquals(Quantity.of(0, "s"), RealScalar.of(0), false);
    _checkEquals(Quantity.of(0, "s*kg^2"), RealScalar.of(2), false);
  }

  public void testEquals2() {
    _checkEquals(RationalScalar.of(0, 1), Quantity.of(0, "m"), false);
    _checkEquals(DoubleScalar.of(0.0), Quantity.of(0, "m"), false);
    _checkEquals(DecimalScalar.of(0.0), Quantity.of(0, "m"), false);
  }

  public void testEquals3() {
    Scalar s1 = Quantity.of(2, "m");
    Scalar s2 = Quantity.of(2, "m^1.0");
    _checkEquals(s1, s2, true);
  }

  public void testCompareEquals() {
    Scalar q1 = Quantity.of(0, "s");
    Scalar q2 = Quantity.of(0, "rad");
    assertFalse(q1.equals(q2));
    try {
      Scalars.compare(q1, q2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Scalars.compare(RealScalar.ZERO, q2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIsZero() {
    Scalar qs1 = Quantity.of(2, "m");
    Scalar qs2 = Quantity.of(3, "m");
    Scalar qs3 = Quantity.of(5, "m");
    assertTrue(Scalars.isZero(qs1.add(qs2).subtract(qs3)));
  }

  private static boolean _isNonNegative(Scalar scalar) {
    return Scalars.lessEquals(scalar.zero(), scalar);
  }

  public void testPredicate() {
    assertTrue(_isNonNegative(Quantity.of(3, "m^2")));
    assertTrue(_isNonNegative(Quantity.of(0, "s*kg")));
    assertFalse(_isNonNegative(Quantity.of(-3, "m")));
  }

  private static void _checkCompareTo(Scalar s1, Scalar s2, int value) {
    int res1 = +Scalars.compare(s1, s2);
    int res2 = -Scalars.compare(s2, s1);
    assertEquals(res1, res2);
    assertEquals(res1, value);
  }

  public void testCompare() {
    _checkCompareTo(Quantity.of(2, "m"), Quantity.of(3, "m"), Integer.compare(2, 3));
    _checkCompareTo(Quantity.of(-3, "m*s"), Quantity.of(7, "s*m"), Integer.compare(-3, 7));
  }

  public void testCompareFail() {
    try {
      _checkCompareTo(Quantity.of(2, "m"), Quantity.of(2, "kg"), Integer.compare(2, 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareFail2() {
    try {
      DoubleScalar.of(3.14).compareTo(Quantity.of(0, "m*s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDistinct() {
    Scalar qs0 = Quantity.of(0, Unit.ONE);
    Scalar qs1 = Quantity.of(0, "m");
    Scalar qs2 = Quantity.of(0, "kg");
    Scalar qs3 = Quantity.of(0, "s");
    Scalar qs4 = Quantity.of(0, "");
    assertTrue(qs4 instanceof RealScalar);
    Tensor vec = Tensors.of(qs0, qs1, qs2, qs3, qs4);
    assertEquals(vec.stream().distinct().count(), 4);
  }
}
