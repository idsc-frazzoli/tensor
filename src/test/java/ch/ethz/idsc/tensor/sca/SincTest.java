// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class SincTest extends TestCase {
  private static Scalar checkBoth(Scalar scalar) {
    Scalar c = Sinc.function.apply(scalar);
    Scalar s = Sin.function.apply(scalar).divide(scalar);
    assertEquals(Chop._12.of(c.subtract(s)), RealScalar.ZERO);
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

  public void testZero() {
    assertEquals(Sinc.function.apply(RealScalar.ZERO), RealScalar.ONE);
  }

  public void testComplex() {
    checkBoth(Sinc.function.apply(ComplexScalar.of(2, 3.)));
    checkBoth(Sinc.function.apply(ComplexScalar.of(-.002, 0.03)));
    checkBoth(Sinc.function.apply(ComplexScalar.of(.002, -0.003)));
    checkBoth(Sinc.function.apply(ComplexScalar.of(-.002, -0.003)));
  }
}
