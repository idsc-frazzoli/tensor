// code by jph
package ch.ethz.idsc.tensor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class ScalarsTest extends TestCase {
  void checkInvariant(String string, Class<?> myclass) {
    Scalar s = Scalars.fromString(string);
    Scalar t = Scalars.fromString(s.toString());
    assertEquals(s, t);
    assertEquals(s.getClass(), myclass);
    assertEquals(t.getClass(), myclass);
  }

  public void testRegex() {
    Pattern pattern = Pattern.compile(StaticHelper.fpRegex);
    Matcher matcher = pattern.matcher("  123");
    assertTrue(matcher.matches()); // TODO this is not entirely consistent
  }

  public void testParse() {
    checkInvariant("123", RationalScalar.class);
    checkInvariant(" 123", DoubleScalar.class); // TODO this is a bit strange behavior
    checkInvariant("3/4", RationalScalar.class);
    checkInvariant("34.23123", DoubleScalar.class);
    checkInvariant("0", ZeroScalar.class);
    checkInvariant("12+15/4*I", ComplexScalar.class);
    checkInvariant("1.0E-50+1.0E50*I", ComplexScalar.class);
    checkInvariant("asndbvf", StringScalar.class);
    checkInvariant("asn.dbv.f", StringScalar.class);
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

  public void testStringScalar() {
    checkInvariant("123123*I", StringScalar.class);
    checkInvariant("123-1A23*I", StringScalar.class);
    checkInvariant("123E-123*I", StringScalar.class);
  }

  public void testParseComplex() {
    checkInvariant(ComplexScalar.of(-1e-14, -1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e-14, -1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e+14, -1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e+14, -1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e-14, -1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e-14, -1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e+14, -1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e+14, -1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e-14, +1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e-14, +1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e+14, +1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e+14, +1e-15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e-14, +1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e-14, +1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(-1e+14, +1e+15).toString(), ComplexScalar.class);
    checkInvariant(ComplexScalar.of(+1e+14, +1e+15).toString(), ComplexScalar.class);
  }
}
