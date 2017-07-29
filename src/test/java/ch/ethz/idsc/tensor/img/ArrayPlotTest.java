// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ArrayPlotTest extends TestCase {
  public void testFail() {
    Tensor matrix = Tensors.fromString("{{0,.1}}");
    Tensor image = ArrayPlot.of(matrix, ColorDataGradients.CLASSIC);
    assertEquals(Dimensions.of(image), Arrays.asList(1, 2, 4));
  }
}
