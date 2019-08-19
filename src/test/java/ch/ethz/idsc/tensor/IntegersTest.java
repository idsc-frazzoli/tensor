// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class IntegersTest extends TestCase {
  public void testPositive() {
    for (int value : new int[] { 1, 2, Integer.MAX_VALUE })
      Integers.requirePositive(value);
  }

  public void testPositiveOrZero() {
    for (int value : new int[] { 0, 1, 2, Integer.MAX_VALUE })
      Integers.requirePositiveOrZero(value);
  }

  public void testPositiveFail() {
    for (int value : new int[] { Integer.MIN_VALUE, -3, -1, 0 })
      try {
        Integers.requirePositive(value);
        fail();
      } catch (Exception exception) {
        // ---
      }
  }

  public void testPositiveOrZeroFail() {
    for (int value : new int[] { Integer.MIN_VALUE, -3, -1 })
      try {
        Integers.requirePositiveOrZero(value);
        fail();
      } catch (Exception exception) {
        // ---
      }
  }
}
