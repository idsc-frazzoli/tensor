// code by jph
package ch.ethz.idsc.tensor.opt;

import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;
import junit.framework.TestCase;

public class PiTest extends TestCase {
  public void testSimple() {
    Scalar pi = Pi.in(new MathContext(100, RoundingMode.HALF_EVEN));
    Scalar sin_pi = Sin.of(pi);
    Scalar cos_pi = Cos.of(pi);
    assertTrue(Chop._40.close(sin_pi, RealScalar.ZERO));
    assertTrue(Chop._40.close(cos_pi, RealScalar.ONE.negate()));
  }

  public void testString() {
    Scalar pi = Pi.in(new MathContext(110, RoundingMode.HALF_EVEN));
    String PI99 = "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706";
    assertTrue(pi.toString().startsWith(PI99));
  }
}
