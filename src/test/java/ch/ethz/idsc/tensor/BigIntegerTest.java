// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

import junit.framework.TestCase;

public class BigIntegerTest extends TestCase {
  public void testReference() {
    assertTrue(BigInteger.valueOf(1) == BigInteger.ONE);
  }
}
