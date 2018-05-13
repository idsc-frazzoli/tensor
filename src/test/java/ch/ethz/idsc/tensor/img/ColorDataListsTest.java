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
    Tensor rgba = ColorDataLists._097.apply(RealScalar.of(2.3));
    assertEquals(rgba, Tensors.fromString("{143, 176, 50, 255}"));
  }

  public void testColor() {
    Color rgba = ColorDataLists._097.getColor(2);
    assertEquals(rgba, new Color(143, 176, 50, 255));
  }

  public void testQuantityTransparent() {
    Tensor rgba = ColorDataLists._103.apply(Quantity.of(2, "s"));
    assertEquals(rgba, Array.zeros(4));
  }

  public void testInfinityTransparent() {
    assertEquals(ColorDataLists._104.apply(DoubleScalar.INDETERMINATE), Array.zeros(4));
    assertEquals(ColorDataLists._106.apply(DoubleScalar.NEGATIVE_INFINITY), Array.zeros(4));
    assertEquals(ColorDataLists._108.apply(DoubleScalar.POSITIVE_INFINITY), Array.zeros(4));
  }

  public void testDerive() {
    for (int alpha = 0; alpha < 256; ++alpha) {
      ColorDataIndexed colorDataIndexed = ColorDataLists._112.deriveWithAlpha(alpha);
      Color color = colorDataIndexed.getColor(3);
      assertEquals(color.getAlpha(), alpha);
    }
  }

  public void testFailNeg() {
    try {
      ColorDataLists._058.apply(RealScalar.of(-0.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize() {
    assertEquals(ColorDataLists._097.size(), 16);
    for (ColorDataIndexed cdi : ColorDataLists.values()) {
      assertTrue(1 < cdi.size());
      assertTrue(cdi.size() < 100);
    }
  }
}
