// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ImageFormatTest extends TestCase {
  public void testFile() {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    assertTrue(file.isFile());
    try {
      BufferedImage bufferedImage = ImageIO.read(file);
      Tensor tensor = ImageFormat.from(bufferedImage);
      assertEquals(tensor.get(14, 0), Tensors.fromString("[254, 0, 0, 255]")); // almost red, fe0000
      assertEquals(Dimensions.of(tensor), Arrays.asList(15, 33, 4));
    } catch (Exception exception) {
      exception.printStackTrace();
      assertTrue(false);
    }
  }

  // public static void main(String[] args) {
  // String f = "/home/datahaki/3rdparty/GHEAT-JAVA/JavaHeatMaps/heatmaps/src/main/resources/res/etc/color-schemes/classic.png";
  // f = "/home/datahaki/image.jpg";
  // try {
  // BufferedImage bi = ImageIO.read(new File(f));
  // Tensor tensor = ImageFormat.from(bi);
  // System.out.println(Dimensions.of(tensor));
  // // Tensor red = tensor.get(-1, -1, 0);
  // // System.out.println(Pretty.of(red));
  // {
  // ImageIO.write(ImageFormat.of(tensor.get(-1, -1)), "png", new File("/home/datahaki/testOrig.png"));
  // ImageIO.write(ImageFormat.of(tensor.get(-1, -1)), "jpg", new File("/home/datahaki/testOrig.jpg"));
  // }
  // {
  // ImageIO.write(ImageFormat.of(tensor.get(-1, -1, 0)), "jpg", new File("/home/datahaki/testR.jpg"));
  // ImageIO.write(ImageFormat.of(tensor.get(-1, -1, 1)), "jpg", new File("/home/datahaki/testG.jpg"));
  // ImageIO.write(ImageFormat.of(tensor.get(-1, -1, 2)), "jpg", new File("/home/datahaki/testB.jpg"));
  // }
  // // System.out.println(Pretty.of(tensor.get(-1, 0, 0)));
  // BufferedImage bi2 = ImageFormat.of(tensor);
  // Tensor t2 = ImageFormat.from(bi2);
  // System.out.println(tensor.equals(t2));
  // System.out.println(Pretty.of(t2.get(0)));
  // } catch (Exception exception) {
  // exception.printStackTrace();
  // }
  // }
}
