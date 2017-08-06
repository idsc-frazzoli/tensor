// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ResourceData;
import junit.framework.TestCase;

public class ColorDataGradientTest extends TestCase {
  public void testSimple() {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    ColorDataGradient cdg = ColorDataGradient.of(tensor);
    assertTrue(Dimensions.of(cdg.rgbaTable()).get(1) == 4);
  }
}
