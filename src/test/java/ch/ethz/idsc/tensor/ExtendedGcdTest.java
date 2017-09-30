// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ExtendedGcdTest extends TestCase {
  public void testSimple() {
    ExtendedGcd egcd = new ExtendedGcd(3, 7);
    assertTrue(egcd.toString().endsWith("true"));
  }
}
