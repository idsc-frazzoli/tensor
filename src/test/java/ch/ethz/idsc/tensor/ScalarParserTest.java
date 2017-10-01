// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ScalarParserTest extends TestCase {
  public void testDouble() {
    assertEquals(ScalarParser.of("3.14`30.123"), ScalarParser.of("3.14"));
  }

  public void testComplex() {
    Scalar c = ScalarParser.of("3.14`30.123+2.12`99.322*I");
    assertEquals(c, ComplexScalar.of(3.14, 2.12));
  }

  public void testImagFormat() {
    assertEquals(ScalarParser.imagToString(RealScalar.of(2.13)), "2.13*I");
    assertEquals(ScalarParser.imagToString(RealScalar.of(-2.13)), "-2.13*I");
  }

  public void testImagFormatI() {
    assertEquals(ScalarParser.imagToString(RealScalar.ONE), "I");
    assertEquals(ScalarParser.imagToString(RealScalar.ONE.negate()), "-I");
  }
}
