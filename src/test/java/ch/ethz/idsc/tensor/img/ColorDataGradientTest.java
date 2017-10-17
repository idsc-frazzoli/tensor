// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ColorDataGradientTest extends TestCase {
  public void testSimple() {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    ColorDataGradient cdg = ColorDataGradient.of(tensor);
    assertEquals(Dimensions.of(cdg.rgbaTable()).get(1), Integer.valueOf(4));
  }

  public void testSerializable() throws Exception {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    ColorDataGradient cdg = ColorDataGradient.of(tensor);
    Serialization.copy(cdg);
  }

  public void testModifiable() {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    ColorDataGradient cdg = ColorDataGradient.of(tensor);
    cdg.apply(RealScalar.ONE).set(RealScalar.ONE, 1);
    cdg.apply(RealScalar.ZERO).set(RealScalar.ONE, 1);
    cdg.apply(DoubleScalar.INDETERMINATE).set(RealScalar.ONE, 1);
  }
}
