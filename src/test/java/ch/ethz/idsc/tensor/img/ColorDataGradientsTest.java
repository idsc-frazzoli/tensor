// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class ColorDataGradientsTest extends TestCase {
  public void testSimple() {
    for (ColorDataFunction cdf : ColorDataGradients.values()) {
      cdf.apply(RealScalar.ZERO);
      cdf.apply(RealScalar.ONE);
    }
  }

  public void testUnmodifiable() {
    Tensor copy = ColorDataGradients.CLASSIC.apply(DoubleScalar.INDETERMINATE);
    try {
      ColorDataGradients.CLASSIC.apply(DoubleScalar.INDETERMINATE).set(Increment.ONE, 1);
    } catch (Exception exception) {
      // ---
    }
    assertEquals(copy, ColorDataGradients.CLASSIC.apply(DoubleScalar.INDETERMINATE));
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
