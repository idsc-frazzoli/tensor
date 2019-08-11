// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ enum ColorDataGradientDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.increasing(Clips.positive(1.0), 255).map(Tensors::of);
    Tensor result = Tensors.empty();
    for (ColorDataGradients colorDataGradients : ColorDataGradients.values())
      result.append(ImageResize.nearest(Transpose.of(domain.map(colorDataGradients)), 8, 1));
    Tensor image = Flatten.of(result, 1);
    System.out.println(Dimensions.of(image));
    Export.of(StaticHelper.image(ColorDataGradients.class), image);
  }
}
