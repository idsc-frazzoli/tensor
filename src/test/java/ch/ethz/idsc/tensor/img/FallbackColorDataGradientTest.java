// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class FallbackColorDataGradientTest extends TestCase {
  public void testNull() {
    assertEquals(FallbackColorDataGradient.INSTANCE.apply(DoubleScalar.INDETERMINATE), Array.zeros(4));
  }

  public void testDerive() {
    assertEquals(FallbackColorDataGradient.INSTANCE.deriveWithOpacity(DoubleScalar.INDETERMINATE), FallbackColorDataGradient.INSTANCE);
  }

  public void testFailNullApply() {
    try {
      FallbackColorDataGradient.INSTANCE.apply(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNullDerive() {
    try {
      FallbackColorDataGradient.INSTANCE.deriveWithOpacity(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
