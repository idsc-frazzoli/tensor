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
import junit.framework.TestCase;

public class RoundTest extends TestCase {
  public void testDouble() {
    assertEquals(Round.function.apply(DoubleScalar.of(11.3)), DoubleScalar.of(11));
    assertEquals(Round.function.apply(DoubleScalar.of(11.5)), DoubleScalar.of(12));
    assertEquals(Round.function.apply(DoubleScalar.of(-11.3)), DoubleScalar.of(-11));
    assertEquals(Round.function.apply(DoubleScalar.of(-11.5)), DoubleScalar.of(-12)); // inconsistent with Math::round
    assertEquals(Round.function.apply(DoubleScalar.of(-11.6)), DoubleScalar.of(-12));
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
    Scalar r = (Scalar) Round.of(s);
    assertEquals(r, RealScalar.of(17));
    assertTrue(r instanceof RationalScalar);
  }

  public void testRational2() {
    Scalar s = RationalScalar.of(734534584545L, 13423656767L); // 54.7194
    Scalar r = (Scalar) Round.of(s);
    assertEquals(r, RealScalar.of(55));
    assertTrue(r instanceof RationalScalar);
  }

  public void testLarge() {
    BigInteger bi = new BigInteger("97826349587623498756234545976");
    Scalar s = RealScalar.of(bi);
    Scalar r = (Scalar) Round.of(s);
    assertTrue(r instanceof RationalScalar);
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
}
