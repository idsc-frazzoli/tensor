// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class BinaryPowerTest extends TestCase {
  public void testInteger() {
    BinaryPower<Integer> binaryPower = new BinaryPower<Integer>() {
      @Override
      public Integer zeroth() {
        return 1;
      }

      @Override
      public Integer invert(Integer integer) {
        if (integer.equals(1))
          return integer;
        throw new RuntimeException();
      }

      @Override
      public Integer multiply(Integer int1, Integer int2) {
        return int1 * int2;
      }
    };
    assertEquals(binaryPower.apply(5, 0), (Integer) 1);
    assertEquals(binaryPower.apply(5, 1), (Integer) 5);
    assertEquals(binaryPower.apply(5, 2), (Integer) 25);
    assertEquals(binaryPower.apply(5, 3), (Integer) 125);
    assertEquals(binaryPower.apply(5, 4), (Integer) 625);
    assertEquals(binaryPower.apply(5, 5), (Integer) 3125);
    assertEquals(binaryPower.apply(1, -3), (Integer) 1);
  }
}
