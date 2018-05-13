// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ColorDataListsTest extends TestCase {
  public void testSimple() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._097.getColorDataIndexed();
    Tensor rgba = colorDataIndexed.apply(RealScalar.of(2.3));
    assertEquals(rgba, Tensors.fromString("{143, 176, 50, 255}"));
  }

  public void testColor() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._097.getColorDataIndexed();
    Color rgba = colorDataIndexed.getColor(2);
    assertEquals(rgba, new Color(143, 176, 50, 255));
  }

  public void testQuantityTransparent() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._103.getColorDataIndexed();
    Tensor rgba = colorDataIndexed.apply(Quantity.of(2, "s"));
    assertEquals(rgba, Array.zeros(4));
  }

  public void testInfinityTransparent() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._104.getColorDataIndexed();
    assertEquals(colorDataIndexed.apply(DoubleScalar.INDETERMINATE), Array.zeros(4));
    assertEquals(colorDataIndexed.apply(DoubleScalar.NEGATIVE_INFINITY), Array.zeros(4));
    assertEquals(colorDataIndexed.apply(DoubleScalar.POSITIVE_INFINITY), Array.zeros(4));
  }

  public void testDerive() {
    ColorDataIndexed master = ColorDataLists._112.getColorDataIndexed();
    for (int alpha = 0; alpha < 256; ++alpha) {
      ColorDataIndexed colorDataIndexed = master.deriveWithAlpha(alpha);
      Color color = colorDataIndexed.getColor(3);
      assertEquals(color.getAlpha(), alpha);
    }
  }

  public void testFailNeg() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._058.getColorDataIndexed();
    try {
      colorDataIndexed.apply(RealScalar.of(-0.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize() {
    ColorDataIndexed master = ColorDataLists._097.getColorDataIndexed();
    assertEquals(master.size(), 16);
    for (ColorDataLists colorDataLists : ColorDataLists.values()) {
      assertTrue(1 < colorDataLists.getColorDataIndexed().size());
      assertTrue(colorDataLists.getColorDataIndexed().size() < 100);
    }
  }
}
