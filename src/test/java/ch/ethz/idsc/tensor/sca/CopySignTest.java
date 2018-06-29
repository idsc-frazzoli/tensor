// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.CopySign;
import junit.framework.TestCase;

public class CopySignTest extends TestCase {
  public void testNonZero() {
    assertEquals(Math.copySign(+2.0, +3.0), +2.0);
    assertEquals(Math.copySign(+2.0, -3.0), -2.0);
    assertEquals(Math.copySign(-2.0, +3.0), +2.0);
    assertEquals(Math.copySign(-2.0, -3.0), -2.0);
    assertEquals(CopySign.of(RealScalar.of(+2), RealScalar.of(+3)), RealScalar.of(+2));
    assertEquals(CopySign.of(RealScalar.of(+2), RealScalar.of(-3)), RealScalar.of(-2));
    assertEquals(CopySign.of(RealScalar.of(-2), RealScalar.of(+3)), RealScalar.of(+2));
    assertEquals(CopySign.of(RealScalar.of(-2), RealScalar.of(-3)), RealScalar.of(-2));
  }

  public void testZero() {
    assertEquals(Math.copySign(+2.0, +0.0), +2.0);
    assertEquals(Math.copySign(+2.0, -0.0), -2.0);
    assertEquals(Math.copySign(-2.0, +0.0), +2.0);
    assertEquals(Math.copySign(-2.0, -0.0), -2.0);
    assertEquals(CopySign.of(RealScalar.of(+2), RealScalar.of(+0.0)), RealScalar.of(+2));
    assertEquals(CopySign.of(RealScalar.of(+2), RealScalar.of(-0.0)), RealScalar.of(+2));
    assertEquals(CopySign.of(RealScalar.of(-2), RealScalar.of(+0.0)), RealScalar.of(+2));
    assertEquals(CopySign.of(RealScalar.of(-2), RealScalar.of(-0.0)), RealScalar.of(+2));
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(5, "s");
    Scalar qs2 = Quantity.of(-3, "m");
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }

  public void testQuantity2() {
    Scalar qs1 = Quantity.of(5, "s");
    Scalar qs2 = RealScalar.of(-3);
    Scalar qs3 = CopySign.of(qs1, qs2);
    assertEquals(qs3, qs1.negate());
  }
}
