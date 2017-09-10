// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

import junit.framework.TestCase;

public class BigIntegerMathTest extends TestCase {
  public void testBigInteger() {
    BigInteger r = BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578084"));
    assertEquals(r, new BigInteger("4589736495873649578"));
    assertEquals(BigIntegerMath.sqrt(BigInteger.ONE), BigInteger.ONE);
    assertEquals(BigIntegerMath.sqrt(BigInteger.ZERO), BigInteger.ZERO);
  }

  public void testBigIntegerFail() {
    try {
      BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578083"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BigIntegerMath.sqrt(new BigInteger("-16"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
