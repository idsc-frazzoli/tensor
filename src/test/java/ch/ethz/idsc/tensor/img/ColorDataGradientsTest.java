// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class ColorDataGradientsTest extends TestCase {
  public void testSimple() {
    for (ColorDataFunction cdf : ColorDataGradients.values()) {
      cdf.apply(RealScalar.ZERO);
      cdf.apply(RealScalar.ONE);
    }
  }

  public void testFail() {
    for (ColorDataFunction cdf : ColorDataGradients.values()) {
      cdf.apply(RealScalar.of(0.5));
      cdf.apply(RealScalar.of(0.99));
      try {
        cdf.apply(RealScalar.of(-0.1));
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
      try {
        cdf.apply(RealScalar.of(1.1));
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
    }
  }
}
