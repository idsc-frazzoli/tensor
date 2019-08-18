// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class InternalTest extends TestCase {
  public void testPositive() {
    for (int value : new int[] { 1, 2, Integer.MAX_VALUE })
      Internal.requirePositive(value);
  }

  public void testPositiveOrZero() {
    for (int value : new int[] { 0, 1, 2, Integer.MAX_VALUE })
      Internal.requirePositiveOrZero(value);
  }

  public void testPositiveFail() {
    for (int value : new int[] { Integer.MIN_VALUE, -3, -1, 0 })
      try {
        Internal.requirePositive(value);
        fail();
      } catch (Exception exception) {
        // ---
      }
  }

  public void testPositiveOrZeroFail() {
    for (int value : new int[] { Integer.MIN_VALUE, -3, -1 })
      try {
        Internal.requirePositiveOrZero(value);
        fail();
      } catch (Exception exception) {
        // ---
      }
  }
}
