// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class AbsSquaredTest extends TestCase {
  public void testQuantity() {
    Scalar qs1 = Quantity.fromString("3+4*I[s^2*m^-1]");
    Scalar qs2 = AbsSquared.FUNCTION.apply(qs1);
    assertEquals(qs2.toString(), "25[m^-2*s^4]");
  }
}
