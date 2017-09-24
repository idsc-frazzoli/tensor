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
  public void testImage1() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
    Tensor image = ImageResize.nearest(tensor, 2);
    assertEquals(Dimensions.of(image), Arrays.asList(66, 30, 4));
  }

  public void testImage2() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
    Tensor image = ImageResize.nearest(tensor, 2, 3);
    assertEquals(Dimensions.of(image), Arrays.asList(66, 45, 4));
  }

  public void testBlub1() {
    Tensor tensor = Tensors.fromString("{{0, 1}, {0, 0}}");
    Tensor resize = ImageResize.nearest(tensor, 3);
    assertEquals(resize.get(1), Tensors.vector(0, 0, 0, 1, 1, 1));
    assertTrue(Chop.NONE.allZero(resize.get(Tensor.ALL, 2)));
    assertEquals(resize.get(Tensor.ALL, 3), Tensors.vector(1, 1, 1, 0, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 4), Tensors.vector(1, 1, 1, 0, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 5), Tensors.vector(1, 1, 1, 0, 0, 0));
  }

  public void testBlub2() {
    Tensor tensor = Tensors.fromString("{{0, 1}, {0, 0}}"); // dims=[2, 2]
    Tensor resize = ImageResize.nearest(tensor, 2, 3); // dims=[4, 6]
    assertEquals(resize.get(1), Tensors.vector(0, 0, 0, 1, 1, 1));
    assertEquals(resize.get(Tensor.ALL, 1), Tensors.vector(0, 0, 0, 0));
    assertTrue(Chop.NONE.allZero(resize.get(Tensor.ALL, 2)));
    assertEquals(resize.get(Tensor.ALL, 3), Tensors.vector(1, 1, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 4), Tensors.vector(1, 1, 0, 0));
    assertEquals(resize.get(Tensor.ALL, 5), Tensors.vector(1, 1, 0, 0));
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
    try {
      ImageResize.nearest(image, -1, 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ImageResize.nearest(image, 2, -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
