// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class FallbackColorDataGradientTest extends TestCase {
  public void testNull() {
    assertEquals(FallbackColorDataGradient.INSTANCE.apply(null), Array.zeros(4));
  }

  public void testDerive() {
    assertEquals(FallbackColorDataGradient.INSTANCE.deriveWithFactor(null), FallbackColorDataGradient.INSTANCE);
  }
}
