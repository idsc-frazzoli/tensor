// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class QuantityParserTest extends TestCase {
  public void testSome() {
    Scalar scalar = QuantityParser.of("3.25[]");
    assertTrue(scalar instanceof DoubleScalar);
    assertEquals(scalar, RealScalar.of(3.25));
  }

  public void testBug() {
    try {
      QuantityParser.of("1[m2]");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
