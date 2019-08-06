// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.num.GaussScalar;
import junit.framework.TestCase;

public class QuantityUnitTest extends TestCase {
  public void testQuantity() {
    assertEquals(QuantityUnit.of(Quantity.of(2, "m*V")), Unit.of("m*V"));
    assertEquals(QuantityUnit.of(Quantity.of(3, "kg*V^-2")), Unit.of("kg*V^-2"));
    assertEquals(QuantityUnit.of(Quantity.of(4, "tpf")), Unit.of("tpf"));
  }

  public void testScalar() {
    assertEquals(QuantityUnit.of(RealScalar.ONE), Unit.ONE);
    assertEquals(QuantityUnit.of(GaussScalar.of(2, 5)), Unit.ONE);
    assertEquals(QuantityUnit.of(StringScalar.of("abc[s]")), Unit.ONE);
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
