// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.Optional;

import junit.framework.TestCase;

public class BigIntegerMathTest extends TestCase {
  public void testZeroOne() {
    assertEquals(BigIntegerMath.sqrt(BigInteger.ZERO).get(), BigInteger.ZERO);
    assertEquals(BigIntegerMath.sqrt(BigInteger.ONE).get(), BigInteger.ONE);
  }

  public void testBigInteger() {
    Optional<BigInteger> sqrt = BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578084"));
    assertEquals(sqrt.get(), new BigInteger("4589736495873649578"));
  }

  public void testBigIntegerFail() {
    Optional<BigInteger> optional = BigIntegerMath.sqrt(new BigInteger("21065681101554527729739161805139578083"));
    assertFalse(optional.isPresent());
  }

  public void testNegativeFail() {
    Optional<BigInteger> optional = BigIntegerMath.sqrt(new BigInteger("-16"));
    assertFalse(optional.isPresent());
  }
}
