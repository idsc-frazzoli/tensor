// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

import junit.framework.TestCase;

public class BigIntegerMathTest extends TestCase {
  public void testZeroOne() {
    assertEquals(BigIntegerMath.sqrt(BigInteger.ZERO), BigInteger.ZERO);
    assertEquals(BigIntegerMath.sqrt(BigInteger.ONE), BigInteger.ONE);
  }

  public void testBigInteger() {
    BigInteger sqrt = BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578084"));
    assertEquals(sqrt, new BigInteger("4589736495873649578"));
  }

  public void testBigIntegerFail() {
    try {
      BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578083"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNegativeFail() {
    try {
      BigIntegerMath.sqrt(new BigInteger("-16"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
