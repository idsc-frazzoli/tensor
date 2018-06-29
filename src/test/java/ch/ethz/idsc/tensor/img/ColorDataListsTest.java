// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ColorDataListsTest extends TestCase {
  public void testApply() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._097.cyclic();
    assertEquals(colorDataIndexed.apply(RealScalar.of(2.3)), Tensors.fromString("{143, 176, 50, 255}"));
  }

  public void testGetColor() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._097.cyclic();
    assertEquals(colorDataIndexed.getColor(2), new Color(143, 176, 50, 255));
  }

  public void testQuantityTransparent() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._103.cyclic();
    assertEquals(colorDataIndexed.apply(Quantity.of(2, "s")), Array.zeros(4));
  }

  public void testInfinityTransparent() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._104.cyclic();
    assertEquals(colorDataIndexed.apply(DoubleScalar.INDETERMINATE), Array.zeros(4));
    assertEquals(colorDataIndexed.apply(DoubleScalar.NEGATIVE_INFINITY), Array.zeros(4));
    assertEquals(colorDataIndexed.apply(DoubleScalar.POSITIVE_INFINITY), Array.zeros(4));
  }

  public void testDerive() {
    ColorDataIndexed master = ColorDataLists._112.cyclic();
    for (int alpha = 0; alpha < 256; ++alpha) {
      ColorDataIndexed colorDataIndexed = master.deriveWithAlpha(alpha);
      Color color = colorDataIndexed.getColor(3);
      assertEquals(color.getAlpha(), alpha);
    }
  }

  public void testFailNeg() {
    ColorDataIndexed colorDataIndexed = ColorDataLists._058.cyclic();
    colorDataIndexed.apply(RealScalar.of(-0.3));
  }

  public void testSize() {
    ColorDataLists colorDataLists = ColorDataLists._097;
    assertEquals(colorDataLists.size(), 16);
  }

  public void testSize2() {
    for (ColorDataLists colorDataLists : ColorDataLists.values()) {
      assertTrue(1 < colorDataLists.size());
      assertTrue(colorDataLists.size() < 100);
    }
  }
}
