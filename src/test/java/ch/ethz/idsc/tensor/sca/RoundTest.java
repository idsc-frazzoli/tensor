// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.StringScalar;
import junit.framework.TestCase;

public class RoundTest extends TestCase {
  public void testDouble() {
    assertEquals(Round.FUNCTION.apply(DoubleScalar.of(11.3)), DoubleScalar.of(11));
    assertEquals(Round.FUNCTION.apply(DoubleScalar.of(11.5)), DoubleScalar.of(12));
    assertEquals(Round.FUNCTION.apply(DoubleScalar.of(-11.3)), DoubleScalar.of(-11));
    assertEquals(Round.FUNCTION.apply(DoubleScalar.of(-11.5)), DoubleScalar.of(-12)); // inconsistent with Math::round
    assertEquals(Round.FUNCTION.apply(DoubleScalar.of(-11.6)), DoubleScalar.of(-12));
  }

  public void testLarge1() {
    BigInteger bi = BigDecimal.valueOf(-999.5).setScale(0, RoundingMode.HALF_UP).toBigIntegerExact();
    assertEquals(bi.toString(), "-1000");
  }

  public void testLarge2() {
    BigInteger bi = BigDecimal.valueOf(1e30).setScale(0, RoundingMode.HALF_UP).toBigIntegerExact();
    assertEquals(bi.toString(), "1000000000000000000000000000000");
  }

  public void testRational1() {
    Scalar s = RationalScalar.of(234534584545L, 13423656767L); // 17.4717
    Scalar r = Round.of(s);
    assertEquals(r, RealScalar.of(17));
    assertTrue(r instanceof RationalScalar);
  }

  public void testRational2() {
    Scalar s = RationalScalar.of(734534584545L, 13423656767L); // 54.7194
    Scalar r = Round.of(s);
    assertEquals(r, RealScalar.of(55));
    assertTrue(r instanceof RationalScalar);
  }

  public void testLarge() {
    BigInteger bi = new BigInteger("97826349587623498756234545976");
    Scalar s = RealScalar.of(bi);
    Scalar r = Round.of(s);
    assertTrue(r instanceof RationalScalar);
    assertEquals(s, r);
  }

  public void testMatsim() {
    Scalar e = DoubleScalar.of(Math.exp(1));
    Scalar b = e.multiply(RealScalar.of(new BigInteger("1000000000000000000000000000000000")));
    Scalar r = Round.of(b);
    // TODO test on arm
    System.out.println(r.toString());
    assertTrue(r.toString().startsWith("2718281828459"));
    assertTrue(r.toString().endsWith("000000000000"));
    // assertEquals(r.toString(), "2718281828459045000000000000000000");
  }

  public void testToMultipleOf1() {
    Scalar s = DoubleScalar.of(3.37151617);
    Scalar sr = Round.toMultipleOf(DecimalScalar.of(.1)).apply(s);
    assertEquals(sr.toString(), "3.4");
  }

  public void testToMultipleOf2() {
    Scalar s = DoubleScalar.of(3.37151617);
    Scalar sr = Round.toMultipleOf(RationalScalar.of(1, 2)).apply(s);
    assertEquals(sr.toString(), "7/2");
  }

  public void testRoundOptions() {
    Scalar pi = DoubleScalar.of(Math.PI);
    assertEquals(pi.map(Round._1).toString(), "3.1");
    assertEquals(pi.map(Round._2).toString(), "3.14");
    assertEquals(pi.map(Round._3).toString(), "3.142");
    assertEquals(pi.map(Round._4).toString(), "3.1416");
    assertEquals(pi.map(Round._5).toString(), "3.14159");
    assertEquals(pi.map(Round._6).toString(), "3.141593");
    assertEquals(pi.map(Round._7).toString(), "3.1415927");
    assertEquals(pi.map(Round._8).toString(), "3.14159265");
    assertEquals(pi.map(Round._9).toString(), "3.141592654");
  }

  public void testRoundOptions2() {
    Scalar pi = Scalars.fromString("3.100000000000008");
    assertEquals(pi.map(Round._1).toString(), "3.1");
    assertEquals(pi.map(Round._2).toString(), "3.10");
    assertEquals(pi.map(Round._3).toString(), "3.100");
    assertEquals(pi.map(Round._4).toString(), "3.1000");
    assertEquals(pi.map(Round._5).toString(), "3.10000");
    assertEquals(pi.map(Round._6).toString(), "3.100000");
    assertEquals(pi.map(Round._7).toString(), "3.1000000");
    assertEquals(pi.map(Round._8).toString(), "3.10000000");
    assertEquals(pi.map(Round._9).toString(), "3.100000000");
  }

  public void testTypeFail() {
    try {
      Round.of(StringScalar.of("some"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
