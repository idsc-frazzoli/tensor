// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class StrictColorDataIndexedTest extends TestCase {
  public void testSimple() {
    Tensor tensor = Tensors.fromString("{{1,2,3,4},{5,6,7,8}}");
    ColorDataIndexed colorDataIndexed = StrictColorDataIndexed.create(tensor);
    assertEquals(colorDataIndexed.apply(RealScalar.of(1.9)), tensor.get(1));
    assertEquals(colorDataIndexed.apply(RealScalar.of(1.9)), tensor.get(1));
    assertEquals(colorDataIndexed.apply(RealScalar.of(0.9)), tensor.get(0));
    final Color ref0 = new Color(1, 2, 3, 4);
    assertEquals(colorDataIndexed.getColor(0), ref0);
    assertEquals(colorDataIndexed.rescaled(0), ref0);
    assertEquals(colorDataIndexed.rescaled(0.4), ref0);
    assertEquals(colorDataIndexed.rescaled(0.6), ref0);
    final Color ref1 = new Color(5, 6, 7, 8);
    assertEquals(colorDataIndexed.getColor(1), ref1);
    assertEquals(colorDataIndexed.rescaled(1), ref1);
  }

  public void testDerive() {
    Tensor tensor = Tensors.fromString("{{1,2,3,4},{5,6,7,8}}");
    ColorDataIndexed colorDataIndexed = StrictColorDataIndexed.create(tensor);
    colorDataIndexed = colorDataIndexed.deriveWithAlpha(255);
    final Color ref0 = new Color(1, 2, 3, 255);
    assertEquals(colorDataIndexed.getColor(0), ref0);
    assertEquals(colorDataIndexed.rescaled(0), ref0);
    assertEquals(colorDataIndexed.rescaled(0.4), ref0);
    assertEquals(colorDataIndexed.rescaled(0.6), ref0);
    final Color ref1 = new Color(5, 6, 7, 255);
    assertEquals(colorDataIndexed.getColor(1), ref1);
    assertEquals(colorDataIndexed.rescaled(1), ref1);
  }

  public void testFail() {
    Tensor tensor = Tensors.fromString("{{1,2,3},{5,6,7}}");
    try {
      StrictColorDataIndexed.create(tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
