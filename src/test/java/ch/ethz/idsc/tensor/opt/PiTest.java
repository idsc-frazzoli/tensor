// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;
import junit.framework.TestCase;

public class PiTest extends TestCase {
  public void testTrigonometry() {
    Scalar pi = Pi.in(100);
    Scalar sin_pi = Sin.of(pi);
    Scalar cos_pi = Cos.of(pi);
    assertTrue(Chop._40.close(sin_pi, RealScalar.ZERO));
    assertTrue(Chop._40.close(cos_pi, RealScalar.ONE.negate()));
  }

  public void testTwo2() {
    assertEquals(Pi.HALF.multiply(RealScalar.of(2)), Pi.VALUE);
    assertEquals(Pi.VALUE.divide(RealScalar.of(2)), Pi.HALF);
  }

  public void testTwo3() {
    assertEquals(Pi.TWO.divide(RealScalar.of(2)), Pi.VALUE);
    assertEquals(Pi.VALUE.multiply(RealScalar.of(2)), Pi.TWO);
  }

  public void testTwo4() {
    assertEquals(Pi.HALF.multiply(RealScalar.of(4)), Pi.TWO);
    assertEquals(Pi.TWO.divide(RealScalar.of(4)), Pi.HALF);
  }

  public void testString() {
    Scalar pi = Pi.in(110);
    String PI99 = "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706";
    assertTrue(pi.toString().startsWith(PI99));
  }

  public void test1000() {
    Scalar pi = Pi.in(1000);
    double value = pi.multiply(RealScalar.of(2.0)).number().doubleValue();
    assertEquals(value, 2 * Math.PI);
  }

  public void testDoublePrecision() {
    assertEquals(Pi.VALUE.number().doubleValue(), Math.PI);
    assertEquals(Pi.HALF.number().doubleValue(), Math.PI * 0.5);
    assertEquals(Pi.TWO.number().doubleValue(), Math.PI / 0.5);
  }
}
