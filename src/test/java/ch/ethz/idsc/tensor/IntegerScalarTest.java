// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

import junit.framework.TestCase;

public class IntegerScalarTest extends TestCase {
  public void testBigInteger() {
    Scalar scalar = IntegerScalar.of(new BigInteger("123"));
    assertTrue(scalar instanceof RationalScalar);
    assertEquals(scalar, RealScalar.of(123));
  }
}
