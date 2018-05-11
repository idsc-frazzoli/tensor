// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.PadLeft;
import ch.ethz.idsc.tensor.img.ColorDataLists;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.utl.GraphicsUtil;
import ch.ethz.idsc.tensor.utl.UserHome;

enum ColorDataListsDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor image = Tensors.empty();
    for (ColorDataLists cdi : ColorDataLists.values()) {
      Tensor vector = Tensors.vector(i -> i < cdi.size() ? RealScalar.of(i) : DoubleScalar.INDETERMINATE, 16);
      image.append(vector.map(cdi));
    }
    image = PadLeft.with(RealScalar.of(255), image.length(), 16 + 2, 4).apply(image);
    System.out.println(Dimensions.of(image));
    int size = 12;
    Tensor large = ImageResize.nearest(image, size);
    BufferedImage bufferedImage = ImageFormat.of(large);
    {
      Graphics2D graphics = bufferedImage.createGraphics();
      GraphicsUtil.setQualityHigh(graphics);
      graphics.setColor(Color.BLACK);
      int y = -1;
      for (ColorDataLists cdi : ColorDataLists.values()) {
        y += size;
        graphics.drawString(cdi.name().substring(1), 0, y);
      }
    }
    large = ImageFormat.from(bufferedImage);
    Export.of(UserHome.Pictures(ColorDataLists.class.getSimpleName() + ".png"), large);
  }
}
