// code by jph
package ch.ethz.idsc.tensor.img;

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
}
