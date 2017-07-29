// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class ColorDataGradientsTest extends TestCase {
  public void testSimple() {
    for (ColorDataFunction cd : ColorDataGradients.values()) {
      cd.apply(RealScalar.ZERO);
      cd.apply(RealScalar.ONE);
    }
  }

  public void testFail() {
    try {
      ColorDataGradients.CLASSIC.apply(RealScalar.of(-0.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ColorDataGradients.CLASSIC.apply(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
