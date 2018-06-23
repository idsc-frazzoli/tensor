// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ColorDataGradientTest extends TestCase {
  public void testDimensions() {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    assertEquals(Dimensions.of(tensor).get(1), Integer.valueOf(4));
    ColorDataGradient.of(tensor);
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

  public void testGaussScalar() {
    Scalar scalar = GaussScalar.of(123, 251);
    try {
      ColorDataGradients.ALPINE.apply(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
