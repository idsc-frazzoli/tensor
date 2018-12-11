// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class WindowFunctionTest extends TestCase {
  public void testSimple() {
    for (WindowFunction windowFunction : WindowFunction.values()) {
      assertEquals(windowFunction.apply(RealScalar.of(-0.500001)), RealScalar.ZERO);
      assertEquals(windowFunction.apply(RealScalar.of(+0.500001)), RealScalar.ZERO);
      assertTrue(Chop._15.close(windowFunction.apply(RealScalar.ZERO), RealScalar.ONE));
    }
  }

  public void testInsideFail() {
    for (WindowFunction windowFunction : WindowFunction.values())
      try {
        windowFunction.apply(Quantity.of(0.1, "s"));
        fail();
      } catch (Exception exception) {
        // ---
      }
  }

  public void testOustideFail() {
    for (WindowFunction windowFunction : WindowFunction.values())
      try {
        windowFunction.apply(Quantity.of(1, "s"));
        fail();
      } catch (Exception exception) {
        // ---
      }
  }
}
