// code by jph
package ch.ethz.idsc.tensor.img;

import java.io.File;
import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.Import;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ImageResizeTest extends TestCase {
  public void testSimple() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(15, 33, 4));
    Tensor image = ImageResize.nearest(tensor, 2);
    assertEquals(Dimensions.of(image), Arrays.asList(30, 66, 4));
  }

  public void testFail() {
    Tensor image = Array.zeros(10, 10, 4);
    ImageResize.nearest(image, 2);
    try {
      ImageResize.nearest(image, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ImageResize.nearest(image, -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBlub() {
    Tensor tensor = Tensors.fromString("{{0, 1}, {0, 0}}");
    Tensor resize = ImageResize.nearest(tensor, 3);
    // System.out.println(Pretty.of(resize));
    assertEquals(resize.get(1), Tensors.vector(0, 0, 0, 1, 1, 1));
    assertTrue(Chop.NONE.allZero(resize.get(Tensor.ALL, 2)));
    assertEquals(resize.get(Tensor.ALL, 3), Tensors.vector(1, 1, 1, 0, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 4), Tensors.vector(1, 1, 1, 0, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 5), Tensors.vector(1, 1, 1, 0, 0, 0));
  }
}
