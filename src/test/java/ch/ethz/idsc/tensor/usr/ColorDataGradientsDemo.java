// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
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
    final int hei = 16;
    final int sep = 120;
    Tensor arr = Array.of(list -> RealScalar.of(list.get(1)), hei, 256);
    Tensor image = Tensors.empty();
    for (ScalarTensorFunction cdf : ColorDataGradients.values())
      image.append(ArrayPlot.of(arr, cdf));
    image = Flatten.of(image, 1);
    List<Integer> dims = Dimensions.of(image);
    dims.set(1, sep);
    image = Join.of(1, image, Array.of(l -> TFF, dims));
    Tensor image2;
    {
      BufferedImage bufferedImage = ImageFormat.of(image);
      Graphics2D graphics = bufferedImage.createGraphics();
      GraphicsUtil.setQualityHigh(graphics);
      int piy = -3;
      graphics.setColor(Color.BLACK);
      for (ColorDataGradients cdg : ColorDataGradients.values()) {
        piy += hei;
        String string = cdg.name().toLowerCase();
        graphics.drawString(string, 256 + 2, piy);
      }
      image2 = ImageFormat.from(bufferedImage);
    }
    int half = image2.length() / 2;
    Tensor top = image2.extract(0, half);
    Tensor bot = image2.extract(half, image2.length());
    Tensor res = Join.of(1, top, bot);
    File file = UserHome.Pictures(ColorDataGradients.class.getSimpleName() + ".png");
    Export.of(file, res);
  }
}
