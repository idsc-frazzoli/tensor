// code by jph
package ch.ethz.idsc.tensor.img;

import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.opt.Pi;
import junit.framework.TestCase;

public class HistogramTransformTest extends TestCase {
  public void testSimple() throws IOException {
    Tensor tensor = ResourceData.of("/io/image/album_au_gray.jpg");
    Tensor result = HistogramTransform.of(tensor);
    assertEquals(Dimensions.of(tensor), Dimensions.of(result));
  }

  public void testScalarFail() throws IOException {
    try {
      HistogramTransform.of(Pi.VALUE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVectorFail() throws IOException {
    try {
      HistogramTransform.of(Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
