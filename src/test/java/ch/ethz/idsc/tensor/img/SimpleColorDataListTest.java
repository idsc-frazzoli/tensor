// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SimpleColorDataListTest extends TestCase {
  public void testSimple() {
    Tensor tensor = Tensors.fromString("{{1,2,3,4},{5,6,7,8}}");
    ColorDataIndexed cdi = SimpleColorDataList.create(tensor);
    assertEquals(cdi.apply(RealScalar.of(1.9)), tensor.get(1));
    assertEquals(cdi.size(), 2);
    Color color = cdi.getColor(0);
    assertEquals(color.getRed(), 1);
    assertEquals(color.getGreen(), 2);
    assertEquals(color.getBlue(), 3);
    assertEquals(color.getAlpha(), 4);
  }
}
