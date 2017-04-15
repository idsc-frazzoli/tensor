// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.DoubleScalar;
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
}
