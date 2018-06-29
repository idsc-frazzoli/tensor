// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ArrayPlotTest extends TestCase {
  public void testSmall() {
    Tensor matrix = Tensors.fromString("{{0,.1}}");
    Tensor image = ArrayPlot.of(matrix, ColorDataGradients.CLASSIC);
    assertEquals(Dimensions.of(image), Arrays.asList(1, 2, 4));
  }

  public void testHue() {
    Tensor matrix = Tensors.fromString("{{0,.1},{1,2},{1.2,.2}}");
    Tensor image = ArrayPlot.of(matrix, HueColorData.FUNCTION);
    assertEquals(Dimensions.of(image), Arrays.asList(3, 2, 4));
  }

  public void testIndexed() {
    Tensor matrix = Tensors.fromString("{{0, 1}, {3, 1}, {3, 2}}");
    for (ColorDataLists colorDataLists : ColorDataLists.values()) {
      Tensor image = matrix.map(colorDataLists.cyclic());
      assertEquals(Dimensions.of(image), Arrays.asList(3, 2, 4));
    }
  }
}
