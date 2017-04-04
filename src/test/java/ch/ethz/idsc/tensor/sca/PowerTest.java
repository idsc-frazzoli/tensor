// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class PowerTest extends TestCase {
  public void testSimple() {
    assertEquals(Power.of(2, 4), RealScalar.of(16));
    assertEquals(Power.of(-4, 5), RealScalar.of(-1024));
  }

  public void testZero() {
    assertEquals(Power.of(2, 0), RealScalar.ONE);
    assertEquals(Power.of(0, 0), RealScalar.ONE);
    // System.out.println(Math.pow(0, 0));
    // try {
    // Power.of(RealScalar.of(0), 0);
    // assertTrue(false);
    // } catch (Exception e) {
    // // ---
    // }
  }

  public void testSqrt() {
    assertEquals(Power.of(2, .5), Sqrt.of(RealScalar.of(2)));
    assertEquals(Power.of(14, .5), Sqrt.of(RealScalar.of(14)));
  }

  public void testNegative() {
    assertEquals(Power.of(2, -4), RationalScalar.of(1, 16));
    assertEquals(Power.of(-4, -5), RationalScalar.of(1, -1024));
  }
}
