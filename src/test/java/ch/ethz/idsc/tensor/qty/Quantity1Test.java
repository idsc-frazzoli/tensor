// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class Quantity1Test extends TestCase {
  public void testSimple() {
    assertTrue(Quantity.fromString("-7[m*kg^-2]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m ]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m *rad ]") instanceof Quantity);
    assertFalse(Quantity.fromString(" 3  ") instanceof Quantity);
  }

  public void testNestFail() {
    Scalar q1 = Quantity.of(3.14, "[m]");
    try {
      Quantity.of(q1, "[s]");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyFail() {
    try {
      Quantity.of(3.14, "[]"); // at the moment empty brackets are not supported
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEquals() {
    assertTrue(RationalScalar.of(0, 1).equals(Quantity.of(0, "[m]")));
    assertTrue(DoubleScalar.of(0.0).equals(Quantity.of(0, "[m]")));
    assertTrue(DecimalScalar.of(0.0).equals(Quantity.of(0, "[m]")));
  }
}
