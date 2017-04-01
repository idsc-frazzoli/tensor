// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.io.Pretty;
import junit.framework.TestCase;

public class ImageFormatTest extends TestCase {
  public void testImage() {
    // ---
  }

  public static void main(String[] args) {
    String f = "/home/datahaki/3rdparty/GHEAT-JAVA/JavaHeatMaps/heatmaps/src/main/resources/res/etc/color-schemes/classic.png";
    try {
      BufferedImage bi = ImageIO.read(new File(f));
      Tensor tensor = ImageFormat.from(bi);
      System.out.println(Dimensions.of(tensor));
      // System.out.println(Pretty.of(tensor.get(-1, 0)));
      // System.out.println(Pretty.of(tensor.get(-1, 0, 0)));
      BufferedImage bi2 = ImageFormat.of(tensor);
      Tensor t2 = ImageFormat.from(bi2);
      System.out.println(tensor.equals(t2));
      System.out.println(Pretty.of(t2.get(-1, 0)));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
