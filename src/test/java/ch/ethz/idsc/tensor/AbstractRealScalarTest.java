// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class AbstractRealScalarTest extends TestCase {
  public void testArcTan() {
    try {
      ArcTan.of(RealScalar.of(2.3), GaussScalar.of(3, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      ArcTan.of(GaussScalar.of(3, 7), RealScalar.of(2.3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRange() {
    assertEquals(Math.log(AbstractRealScalar.LOG_HI), Math.log1p(AbstractRealScalar.LOG_HI - 1));
    assertEquals(Math.log(AbstractRealScalar.LOG_LO), Math.log1p(AbstractRealScalar.LOG_LO - 1));
  }

  public void testPower00() {
    Scalar one = Power.of(0, 0);
    ExactScalarQ.require(one);
    assertEquals(one, RealScalar.ONE);
  }

  public void testPower00Numeric() {
    Scalar one = Power.of(0.0, 0.0);
    assertTrue(ExactScalarQ.of(one));
    assertEquals(one, RealScalar.ONE);
  }

  public void testPowerFail() {
    try {
      Power.of(0, GaussScalar.of(2, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Power.of(1, GaussScalar.of(2, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
