// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class BigFractionTest extends TestCase {
  public void testCompactString() {
    assertEquals(BigFraction.of(24, 1).toString(), "24");
    assertEquals(BigFraction.of(24, -1).toString(), "-24");
    assertEquals(BigFraction.of(24, 7).toString(), "24/7");
    assertEquals(BigFraction.of(24, -7).toString(), "-24/7");
  }

  public void testDivide() {
    BigFraction num = BigFraction.of(1, 1);
    BigFraction den = BigFraction.of(0, 1);
    try {
      num.divide(den);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testHash() {
    assertEquals(BigFraction.of(7, 3).hashCode(), 1181);
    assertEquals(BigFraction.of(3, 7).hashCode(), 1061);
  }

  public void testEquals() {
    assertFalse(BigFraction.of(7, 3).equals(null));
    assertFalse(BigFraction.of(7, 3).equals("abc"));
  }

  public void testDenZero() {
    try {
      BigFraction.of(3, 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPackageVisibility() {
    int modifiers = BigFraction.class.getModifiers();
    assertEquals(modifiers & 0x1, 0x0); // non public but package
  }
}
