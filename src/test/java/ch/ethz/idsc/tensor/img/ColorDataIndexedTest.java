// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.ComplexScalar;
import junit.framework.TestCase;

public class ColorDataIndexedTest extends TestCase {
  public void testLumaPalette() {
    assertEquals(ColorDataLists._250.size(), 13);
    assertEquals(ColorDataLists._250.cyclic().getColor(0), new Color(241, 0, 0, 255));
  }

  public void testFailComplex() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._058.cyclic();
    try {
      colorDataIndexed.apply(ComplexScalar.of(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDeriveFail() {
    try {
      ColorDataLists._250.cyclic().deriveWithAlpha(256);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ColorDataLists._250.cyclic().deriveWithAlpha(-1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
