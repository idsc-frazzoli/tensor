// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;
import ch.ethz.idsc.tensor.utl.GraphicsUtil;
import ch.ethz.idsc.tensor.utl.UserHome;

/** export of available {@link ColorDataGradients} to a single image */
enum ColorDataGradientsDemo {
  ;
  private static final Scalar TFF = RealScalar.of(255);

  public static void main(String[] args) throws IOException {
    final int spa = 0;
    final int hei = 15 + spa;
    final int sep = 142;
    Tensor arr = Array.of(list -> RealScalar.of(list.get(1)), hei - spa, 256);
    Tensor image = Tensors.empty();
    Tensor white = Array.of(l -> TFF, hei - spa, sep, 4);
    for (ScalarTensorFunction cdf : ColorDataGradients.values()) {
      image.append(Join.of(1, ArrayPlot.of(arr, cdf), white));
      image.append(Array.zeros(spa, 256 + sep, 4));
    }
    image = Flatten.of(image, 1);
    {
      BufferedImage bufferedImage = ImageFormat.of(image);
      Graphics2D graphics = bufferedImage.createGraphics();
      GraphicsUtil.setQualityHigh(graphics);
      int piy = -3 - spa;
      graphics.setColor(Color.BLACK);
      for (ColorDataGradients cdg : ColorDataGradients.values()) {
        piy += hei;
        String string = cdg.name();
        graphics.drawString(string, 256 + 2, piy);
      }
      image = ImageFormat.from(bufferedImage);
    }
    int half = image.length() / 2;
    Tensor top = image.extract(0, half);
    Tensor bot = image.extract(half, image.length());
    Tensor res = Join.of(1, top, bot);
    File file = UserHome.Pictures(ColorDataGradients.class.getSimpleName() + ".png");
    Export.of(file, res);
  }
}
