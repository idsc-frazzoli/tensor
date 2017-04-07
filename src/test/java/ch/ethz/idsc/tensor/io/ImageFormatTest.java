// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ImageFormatTest extends TestCase {
  public void testRGBAFile() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    assertTrue(file.isFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor tensor = ImageFormat.from(bufferedImage);
    assertEquals(tensor.get(12, 19), Tensors.fromString("[118, 130, 146, 200]"));
    assertEquals(tensor.get(14, 0), Tensors.fromString("[254, 0, 0, 255]")); // almost red, fe0000
    assertEquals(Dimensions.of(tensor), Arrays.asList(15, 33, 4));
  }

  public void testGrayFile() throws Exception {
    File file = new File(getClass().getResource("/io/gray15x9.png").getFile());
    assertTrue(file.isFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor tensor = ImageFormat.fromGrayscale(bufferedImage);
    assertEquals(tensor.Get(2, 0), RealScalar.of(216));
    assertEquals(Dimensions.of(tensor), Arrays.asList(15, 9));
  }
}
