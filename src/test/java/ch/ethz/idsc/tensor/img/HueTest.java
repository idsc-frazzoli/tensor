// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import junit.framework.TestCase;

public class HueTest extends TestCase {
  public void testMod() {
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
