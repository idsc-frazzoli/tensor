// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class QuantityMagnitudeTest extends TestCase {
  public void testSimple() {
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    Scalar q = Quantity.of(2, "km^2");
    Unit unit = Unit.of("m^2");
    Scalar scalar = quantityMagnitude.in(unit).apply(q);
    assertEquals(scalar, RealScalar.of(2_000_000));
  }
}
