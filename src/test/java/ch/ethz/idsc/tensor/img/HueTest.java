// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class HueTest extends TestCase {
  public void testSimple() {
    assertEquals(Hue.of(0, 1, 1, 1), Hue.of(1, 1, 1, 1));
    assertEquals(Hue.of(0.2, 1, 1, 1), Hue.of(3.2, 1, 1, 1));
    assertEquals(Hue.of(0.2, 1, 1, 1), Hue.of(-3.8, 1, 1, 1));
  }

  public void testAlpha() {
    assertEquals(Hue.of(0.2, 1, 1, 1).getAlpha(), 255);
    assertEquals(Hue.of(0.1, 1, 1, .5).getAlpha(), 128);
    assertEquals(Hue.of(0.1, 1, 1, 0).getAlpha(), 0);
  }

  public void testSaturationEps() {
    assertEquals(Hue.of(0.1, Math.nextUp(0.0), .2, 0), Hue.of(0.1, 0.0, .2, 0));
    assertEquals(Hue.of(0.1, Math.nextUp(0.0), 1, 1), Color.WHITE);
  }

  public void testSome() {
    Tensor color = Hue.COLORDATA.apply(RealScalar.of(0.1));
    Tensor alter = ColorDataGradients.HUE.apply(RealScalar.of(0.1));
    assertTrue(Chop._05.close(color, alter));
    assertEquals(Hue.COLORDATA.apply(RealScalar.ONE), ColorFormat.toVector(Color.RED));
  }

  public void testFail() {
    try {
      Hue.of(0, 0, 1.01, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Hue.of(Double.POSITIVE_INFINITY, 1, 1, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
