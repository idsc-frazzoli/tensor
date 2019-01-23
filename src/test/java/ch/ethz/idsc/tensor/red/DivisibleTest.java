// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class DivisibleTest extends TestCase {
  public void testSimple() {
    assertTrue(Divisible.of(RealScalar.of(9), RealScalar.of(3)));
    assertTrue(Divisible.of(RationalScalar.of(18, 7), RationalScalar.of(3, 7)));
    assertFalse(Divisible.of(RationalScalar.of(8, 7), RationalScalar.of(3, 7)));
  }

  public void testComplex() {
    Scalar c2 = ComplexScalar.of(2, 3);
    Scalar c1 = c2.multiply(RealScalar.of(3));
    assertTrue(Divisible.of(c1, c2));
    assertFalse(Divisible.of(c2, c1));
  }

  public void testGaussian() {
    Scalar c1 = ComplexScalar.of(3, 1);
    Scalar c2 = ComplexScalar.of(2, -1);
    assertTrue(Divisible.of(c1, c2));
    assertFalse(Divisible.of(c2, c1));
  }

  public void testQuantity() {
    assertTrue(Divisible.of(Quantity.of(9, "m"), Quantity.of(3, "m")));
    assertFalse(Divisible.of(Quantity.of(7, "m"), Quantity.of(3, "m")));
    assertFalse(Divisible.of(Quantity.of(3, "m"), Quantity.of(7, "m")));
  }

  public void testQuantityIncompatible() {
    Scalar qs1 = Quantity.of(6, "m");
    Scalar qs2 = Quantity.of(3, "s");
    try {
      Divisible.of(qs1, qs2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumericFail() {
    try {
      Divisible.of(RealScalar.of(9.), RealScalar.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Divisible.of(Quantity.of(9., "m"), Quantity.of(3, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Divisible.of(null, RealScalar.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Divisible.of(Quantity.of(9, "m"), null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
