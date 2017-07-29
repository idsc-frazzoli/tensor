// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class ColorDataFunctionTest extends TestCase {
  public void testCustom() {
    ColorDataFunction template = ColorDataGradients.CLASSIC;
    ColorDataFunction custom = s -> {
      Tensor rgba = template.apply(s);
      rgba.set(RealScalar.of(128), 3);
      return rgba;
    };
    Tensor rgba = custom.apply(RealScalar.of(.1));
    assertEquals(rgba.length(), 4);
    assertEquals(rgba.get(3), RealScalar.of(128));
  }
}
