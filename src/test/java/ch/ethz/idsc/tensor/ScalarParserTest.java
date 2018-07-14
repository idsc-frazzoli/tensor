// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ScalarParserTest extends TestCase {
  public void testDouble() {
    assertEquals(ScalarParser.of("3.14`30.123"), ScalarParser.of("3.14"));
  }

  public void testComplex() {
    Scalar scalar = ScalarParser.of("3.14`30.123+2.12`99.322*I");
    assertEquals(scalar, ComplexScalar.of(3.14, 2.12));
  }

  public void testDivisions() {
    Scalar scalar = ScalarParser.of("40*3/4/5/6");
    assertEquals(scalar, ComplexScalar.of(1, 0));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testMix() {
    Scalar scalar = ScalarParser.of("80*3/4/2*5/6");
    assertEquals(scalar, ComplexScalar.of(25, 0));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testImagFormat() {
    assertEquals(ScalarParser.imagToString(RealScalar.of(2.13)), "2.13*I");
    assertEquals(ScalarParser.imagToString(RealScalar.of(-2.13)), "-2.13*I");
  }

  public void testImagFormatI() {
    assertEquals(ScalarParser.imagToString(RealScalar.ONE), "I");
    assertEquals(ScalarParser.imagToString(RealScalar.ONE.negate()), "-I");
  }

  public void testRational1() {
    Scalar z = ComplexScalar.of(RationalScalar.of(1, 2), RationalScalar.of(1, 3));
    assertEquals(Scalars.fromString(z.toString()), z);
  }

  public void testRational2() {
    Scalar z = ComplexScalar.of(RationalScalar.of(1, 2), RationalScalar.of(-1, 3));
    assertEquals(Scalars.fromString(z.toString()), z);
  }

  public void testDecimal() {
    assertEquals(ComplexScalar.of(RealScalar.ZERO, DecimalScalar.of(1)).toString(), "I");
    assertEquals(ComplexScalar.of(RealScalar.ZERO, DecimalScalar.of(-1)).toString(), "-I");
  }
}
