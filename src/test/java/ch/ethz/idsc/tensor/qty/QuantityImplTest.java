// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class QuantityImplTest extends TestCase {
  public void testSerializable() throws Exception {
    Quantity quantity = (Quantity) Scalars.fromString("-7+3*I[kg^-2*m*s]");
    Quantity copy = Serialization.copy(quantity);
    assertEquals(quantity, copy);
  }

  public void testExactIntFail() {
    try {
      Scalar scalar = Quantity.of(10, "m");
      Scalars.intValueExact(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEquals() {
    assertFalse(Quantity.of(10, "m").equals("s"));
    assertFalse(Quantity.of(10, "m").equals(Quantity.of(2, "m")));
    assertFalse(Quantity.of(10, "m").equals(Quantity.of(10, "kg")));
  }

  public void testEqualsZero() {
    assertFalse(Quantity.of(0, "m").equals(RealScalar.ZERO));
  }

  public void testHashCode() {
    assertEquals( //
        Quantity.of(10.2, "m^-1*kg").hashCode(), //
        Quantity.of(10.2, "kg*m^-1").hashCode());
  }

  public void testEmpty() {
    Scalar q1 = Quantity.of(3, "m*s");
    Scalar q2 = Quantity.of(7, "s*m");
    Scalar s3 = q1.divide(q2);
    assertTrue(s3 instanceof RationalScalar);
    assertTrue(q1.under(q2) instanceof RationalScalar);
  }
}
