// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class QuantityUnitTest extends TestCase {
  public void testQuantity() {
    assertEquals(QuantityUnit.of(Quantity.of(2, "m*V")), Unit.of("m*V"));
    assertEquals(QuantityUnit.of(Quantity.of(2, "kg*V^-2")), Unit.of("kg*V^-2"));
  }

  public void testScalar() {
    assertEquals(QuantityUnit.of(RealScalar.ONE), Unit.ONE);
  }

  public void testNullFail() {
    try {
      QuantityUnit.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
