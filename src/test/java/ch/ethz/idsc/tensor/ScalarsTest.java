// code by jph
package ch.ethz.idsc.tensor;

import java.util.regex.Pattern;

import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import junit.framework.TestCase;

public class ScalarsTest extends TestCase {
  void checkInvariant(String string, Class<?> myclass) {
    Scalar s = Scalars.fromString(string);
    Scalar t = Scalars.fromString(s.toString());
    assertEquals(s, t);
    assertEquals(s.getClass(), myclass);
    assertEquals(t.getClass(), myclass);
  }

  public void testParse() {
    checkInvariant("123", RationalScalar.class);
    checkInvariant("  123  ", RationalScalar.class);
    checkInvariant("3 /  4", RationalScalar.class);
    checkInvariant("34.23123", DoubleScalar.class);
    checkInvariant("0", RationalScalar.class);
    checkInvariant("12+15 /4*I", ComplexScalarImpl.class);
    checkInvariant("1.0E-50 + 1.0E50*I", ComplexScalarImpl.class);
    checkInvariant("I", ComplexScalarImpl.class);
    checkInvariant(" ( I ) ", ComplexScalarImpl.class);
    checkInvariant("123123*I", ComplexScalarImpl.class);
    checkInvariant("123E-123*I", ComplexScalarImpl.class);
    checkInvariant("asndbvf", StringScalar.class);
    checkInvariant("asn.dbv.f", StringScalar.class);
    checkInvariant("123-1A23*I", StringScalar.class);
  }

  public void testSpacing() {
    checkInvariant("-1.0348772853950305  +  0.042973906265653894 * I", ComplexScalarImpl.class);
    checkInvariant("-1.0348772853950305  -  0.042973906265653894 * I", ComplexScalarImpl.class);
  }

  public void testIntegerPattern() {
    String n1 = "-123123";
    String n2 = "123123";
    Pattern pattern = Pattern.compile("-?\\d+");
    assertTrue(pattern.matcher(n1).matches());
    assertTrue(pattern.matcher(n2).matches());
  }

  public void testRationalPattern() {
    String n1 = "-123/123";
    String n2 = "1231/23";
    String n3 = "123123";
    Pattern pattern = Pattern.compile("-?\\d+/\\d+");
    assertTrue(pattern.matcher(n1).matches());
    assertTrue(pattern.matcher(n2).matches());
    assertFalse(pattern.matcher(n3).matches());
  }

  public void testParseComplex() {
    checkInvariant(ComplexScalar.of(-1e-14, -1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e-14, -1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e+14, -1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e+14, -1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e-14, -1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e-14, -1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e+14, -1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e+14, -1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e-14, +1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e-14, +1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e+14, +1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e+14, +1e-15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e-14, +1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e-14, +1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(-1e+14, +1e+15).toString(), ComplexScalarImpl.class);
    checkInvariant(ComplexScalar.of(+1e+14, +1e+15).toString(), ComplexScalarImpl.class);
  }

  public void testImagUnit() {
    assertEquals("I", ComplexScalar.I.toString());
    assertEquals("-I", ComplexScalar.I.negate().toString());
    assertEquals("2+I", RealScalar.of(2).add(ComplexScalar.I).toString());
    assertEquals("2-I", RealScalar.of(2).subtract(ComplexScalar.I).toString());
    // ---
    assertEquals("3*I", ComplexScalar.of(0, 3).toString());
    assertEquals("3-3*I", ComplexScalar.of(3, -3).toString());
    assertEquals("3+3*I", ComplexScalar.of(3, 3).toString());
    assertEquals("-3*I", ComplexScalar.of(0, -3).toString());
    assertEquals("-3-3*I", ComplexScalar.of(-3, -3).toString());
    assertEquals("-3+3*I", ComplexScalar.of(-3, 3).toString());
  }

  public void testNumber() {
    Number a = 123;
    Number b = 123.0;
    assertFalse(a.equals(b));
  }

  private static void checkCmp(double d1, double d2) {
    assertEquals(Double.compare(d1, d2), Scalars.compare(RealScalar.of(d1), RealScalar.of(d2)));
  }

  public void testExtreme() {
    checkInvariant(DoubleScalar.NEGATIVE_INFINITY.toString(), DoubleScalar.class);
    checkInvariant(DoubleScalar.POSITIVE_INFINITY.toString(), DoubleScalar.class);
  }

  public void testCompare() {
    checkCmp(0, 0);
    checkCmp(1, 0);
    checkCmp(1.1, 1.1);
    checkCmp(1, 5);
    checkCmp(-1e10, 5);
    checkCmp(Double.POSITIVE_INFINITY, 5);
    checkCmp(Double.NEGATIVE_INFINITY, 5);
    checkCmp(0, Double.POSITIVE_INFINITY);
    checkCmp(0, Double.NEGATIVE_INFINITY);
    checkCmp(-10, Double.POSITIVE_INFINITY);
    checkCmp(-30, Double.NEGATIVE_INFINITY);
  }

  public void testLessThan() {
    assertFalse(Scalars.lessThan(RealScalar.of(2), RealScalar.of(2)));
    assertTrue(Scalars.lessThan(RealScalar.of(2), RealScalar.of(3)));
    assertTrue(Scalars.lessThan(RealScalar.of(-3), RealScalar.ZERO));
  }

  public void testLessEquals() {
    assertTrue(Scalars.lessEquals(RealScalar.of(2), RealScalar.of(2)));
    assertTrue(Scalars.lessEquals(RealScalar.of(2), RealScalar.of(3)));
    assertTrue(Scalars.lessEquals(RealScalar.of(-3), RealScalar.ZERO));
  }

  public void testIntValueExact() {
    assertEquals(Scalars.intValueExact(RealScalar.of(123)), 123);
  }

  public void testIntValueExactFail() {
    try {
      Scalars.intValueExact(RealScalar.of(Long.MAX_VALUE));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLongValueExact() {
    assertEquals(Scalars.longValueExact(RealScalar.of(Long.MAX_VALUE)), Long.MAX_VALUE);
  }

  public void testExample() {
    Scalar s = Scalars.fromString("(3+2)*I/(-1+4)+8-I");
    Scalar c = ComplexScalar.of(RealScalar.of(8), RationalScalar.of(2, 3));
    assertEquals(c, s);
    assertEquals(s, c);
  }

  public void testParseFail() {
    assertTrue(StringScalarQ.of(Scalars.fromString("(3+2)(-1+4")));
    assertTrue(Scalars.fromString("(3+2)(-1+4+") instanceof StringScalar);
    assertTrue(Scalars.fromString("3+2-1+4+") instanceof StringScalar);
    assertTrue(Scalars.fromString("3+2-1+4-") instanceof StringScalar);
    assertTrue(Scalars.fromString("3++4") instanceof StringScalar);
    assertTrue(Scalars.fromString("3--4") instanceof StringScalar);
    assertTrue(Scalars.fromString("3**4") instanceof StringScalar);
    assertTrue(StringScalarQ.of(Scalars.fromString("3//4")));
  }
}
