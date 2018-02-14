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
    Tensor rgba = ColorDataLists._097.apply(Quantity.of(2, "s"));
    assertEquals(rgba, Array.zeros(4));
  }

  public void testInfinityTransparent() {
    assertEquals(ColorDataLists._097.apply(DoubleScalar.INDETERMINATE), Array.zeros(4));
    assertEquals(ColorDataLists._097.apply(DoubleScalar.NEGATIVE_INFINITY), Array.zeros(4));
    assertEquals(ColorDataLists._097.apply(DoubleScalar.POSITIVE_INFINITY), Array.zeros(4));
  }

  public void testFailNeg() {
    try {
      ColorDataLists._097.apply(RealScalar.of(-0.3));
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
