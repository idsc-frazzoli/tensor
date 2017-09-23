// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.CopySign;
import junit.framework.TestCase;

public class CopySignTest extends TestCase {
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
