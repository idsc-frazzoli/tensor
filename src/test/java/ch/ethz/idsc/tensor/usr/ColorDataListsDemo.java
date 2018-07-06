// code by jph
package ch.ethz.idsc.tensor.usr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.PadLeft;
import ch.ethz.idsc.tensor.alg.PadRight;
import ch.ethz.idsc.tensor.img.ColorDataLists;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.utl.GraphicsUtil;
import ch.ethz.idsc.tensor.utl.UserHome;

enum ColorDataListsDemo {
  ;
  static String csv(String name) {
    if (name.charAt(0) == '_')
      name = name.substring(1);
    return name.toLowerCase();
  }

  public static void main(String[] args) throws IOException {
    Tensor image = Tensors.empty();
    for (ColorDataLists cdi : ColorDataLists.values()) {
      Tensor vector = Tensors.vector(i -> i < cdi.size() ? RealScalar.of(i) : DoubleScalar.INDETERMINATE, 16);
      image.append(vector.map(cdi.cyclic()));
    }
    image = PadLeft.with(RealScalar.of(255), image.length(), 16 + 2, 4).apply(image);
    int ceil = Ceiling.FUNCTION.apply(RationalScalar.of(image.length(), 3)).multiply(RealScalar.of(3)).number().intValue();
    image = PadRight.with(RealScalar.of(0), ceil, 19, 4).apply(image);
    int spa = 0;
    int size = 12 + spa;
    Tensor large = ImageResize.nearest(image, size, size - spa);
    // System.out.println(Dimensions.of(large));
    for (int count = size - spa; count < large.length(); count += size)
      for (int i = 0; i < spa; ++i)
        large.set(s -> RealScalar.ZERO, count + i, Tensor.ALL, Tensor.ALL);
    BufferedImage bufferedImage = ImageFormat.of(large);
    {
      Graphics2D graphics = bufferedImage.createGraphics();
      GraphicsUtil.setQualityHigh(graphics);
      graphics.setColor(Color.BLACK);
      int y = -1 - spa;
      for (ColorDataLists cdi : ColorDataLists.values()) {
        y += size;
        graphics.drawString(csv(cdi.name()), 0, y);
      }
    }
    large = ImageFormat.from(bufferedImage);
    int split = large.length() / 3;
    large = Join.of(1, large.extract(0, split), large.extract(split, 2 * split), large.extract(2 * split, 3 * split));
    Export.of(UserHome.Pictures(ColorDataLists.class.getSimpleName() + ".png"), large);
  }
}
