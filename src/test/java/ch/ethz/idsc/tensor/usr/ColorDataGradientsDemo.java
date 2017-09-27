// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataFunction;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.utl.UserHome;

/** export of available {@link ColorDataGradients} to a single image */
enum ColorDataGradientsDemo {
  ;
  public static void main(String[] args) throws IOException {
    final int hei = 16;
    final int sep = 120;
    Tensor arr = Array.of(list -> RealScalar.of(list.get(1)), hei, 256);
    Tensor image = Tensors.empty();
    for (ColorDataFunction cdf : ColorDataGradients.values())
      image.append(ArrayPlot.of(arr, cdf));
    image = Flatten.of(image, 1);
    List<Integer> dims = Dimensions.of(image);
    dims.set(1, sep);
    image = Join.of(1, Array.of(l -> RealScalar.of(255), dims), image);
    System.out.println(Dimensions.of(image));
    BufferedImage bufferedImage = ImageFormat.of(image);
    Graphics graphics = bufferedImage.getGraphics();
    FontMetrics fm = graphics.getFontMetrics();
    int piy = -3;
    graphics.setColor(Color.BLACK);
    for (ColorDataGradients cdg : ColorDataGradients.values()) {
      piy += hei;
      String string = cdg.name().toLowerCase();
      int width = fm.stringWidth(string);
      graphics.drawString(string, sep - width - 2, piy);
    }
    Export.of(UserHome.Pictures(ColorDataGradients.class.getSimpleName() + ".png"), //
        ImageFormat.from(bufferedImage));
  }
}
