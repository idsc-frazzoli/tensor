// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataFunction;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.utl.UserHome;

/** export of available {@link ColorDataGradients} to a single image */
enum ColorDataGradientsDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor arr = Array.of(list -> RealScalar.of(list.get(1)), 10, 256);
    Tensor image = Tensors.empty();
    for (ColorDataFunction cdf : ColorDataGradients.values())
      image.append(ArrayPlot.of(arr, cdf));
    image = Flatten.of(image, 1);
    Export.of(UserHome.Pictures(ColorDataGradientsDemo.class.getSimpleName() + ".png"), ImageResize.nearest(image, 2));
  }
}
