// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class SincTest extends TestCase {
  private static Scalar checkBoth(Scalar scalar) {
    Scalar c = Sinc.of(scalar);
    Scalar s = Sin.of(scalar).divide(scalar);
    assertEquals(Chop._15.of(c.subtract(s)), RealScalar.ZERO);
    return c;
  }

  public void testDouble() {
    double value = .01;
    Scalar c = checkBoth(RealScalar.of(value));
    Scalar s = DoubleScalar.of(Math.sin(value) / value);
    assertEquals(c, s);
  }

  public void testReal() {
    checkBoth(RealScalar.of(.5));
    checkBoth(RealScalar.of(.1));
    checkBoth(RealScalar.of(.05));
    checkBoth(RealScalar.of(.01));
    checkBoth(RealScalar.of(.005));
    checkBoth(RealScalar.of(.001));
    checkBoth(RealScalar.of(.0005));
    checkBoth(RealScalar.of(.0001));
  }

  public void testReal2() {
    checkBoth(RealScalar.of(-.5));
    checkBoth(RealScalar.of(-.1));
    checkBoth(RealScalar.of(-.05));
    checkBoth(RealScalar.of(-.01));
    checkBoth(RealScalar.of(-.005));
    checkBoth(RealScalar.of(-.001));
    checkBoth(RealScalar.of(-.0005));
    checkBoth(RealScalar.of(-.0001));
  }

  public void testZero() {
    assertEquals(Sinc.of(RealScalar.ZERO), RealScalar.ONE);
  }

  public void testComplex() {
    checkBoth(Sinc.of(ComplexScalar.of(2, 3.)));
    checkBoth(Sinc.of(ComplexScalar.of(-.002, 0.03)));
    checkBoth(Sinc.of(ComplexScalar.of(.002, -0.003)));
    checkBoth(Sinc.of(ComplexScalar.of(-.002, -0.003)));
  }

  public void testThreshold() {
    Scalar res1 = Sinc.of(Sinc.THRESHOLD);
    double val1 = Sinc.THRESHOLD.number().doubleValue();
    double val0 = val1;
    for (int c = 0; c < 100; ++c)
      val0 = Math.nextDown(val0);
    Scalar res0 = Sinc.of(DoubleScalar.of(val0));
    assertEquals(res1, res0);
  }

  public void testTypeFail() {
    try {
      Sinc.of(StringScalar.of("some"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
