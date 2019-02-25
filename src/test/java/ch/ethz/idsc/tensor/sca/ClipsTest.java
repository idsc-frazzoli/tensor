// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipsTest extends TestCase {
  public void testUnitNumeric() {
    Scalar max = Clips.unitNumeric().apply(RealScalar.of(5.0));
    assertEquals(max, RealScalar.ONE);
    assertFalse(ExactScalarQ.of(max));
  }

  public void testInsideFail() {
    try {
      Clips.unit().isInside(Quantity.of(0.5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      Clips.unit().apply(Quantity.of(-5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Clips.absoluteOne().apply(Quantity.of(-5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
