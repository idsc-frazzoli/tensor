// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.StringScalar;
import junit.framework.TestCase;

public class SignTest extends TestCase {
  public void testSome() {
    assertEquals(Sign.of(DoubleScalar.of(-345)), RealScalar.of(-1));
    assertEquals(Sign.of(DoubleScalar.of(9324.5)), RealScalar.of(1));
    assertEquals(Sign.of(RealScalar.ZERO), RealScalar.ZERO);
  }

  public void testDiff() {
    assertEquals(Sign.of(RealScalar.of(3 - 9)), RealScalar.ONE.negate());
    assertEquals(Sign.of(RealScalar.of(3 - 3)), RealScalar.ZERO);
    assertEquals(Sign.of(RealScalar.of(9 - 3)), RealScalar.ONE);
  }

  public void testInfinity() {
    assertEquals(Sign.of(DoubleScalar.POSITIVE_INFINITY), RealScalar.of(+1));
    assertEquals(Sign.of(DoubleScalar.NEGATIVE_INFINITY), RealScalar.of(-1));
  }

  public void testFail() {
    try {
      Sign.of(DoubleScalar.INDETERMINATE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testTypeFail() {
    try {
      Sign.of(StringScalar.of("string"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}