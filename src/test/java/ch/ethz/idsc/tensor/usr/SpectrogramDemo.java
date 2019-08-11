// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.img.ImageResize;
import ch.ethz.idsc.tensor.img.Spectrogram;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.Cos;

/** Example from Mathematica::Spectrogram:
 * Table[Cos[ i/4 + (i/20)^2], {i, 2000}] */
/* package */ enum SpectrogramDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor data = Cos.of(Subdivide.of(0, 100, 2000).map(Series.of(Tensors.vector(0, 5, 1))));
    Tensor image = Spectrogram.of(data, ColorDataGradients.VISIBLESPECTRUM);
    Export.of(StaticHelper.image(Spectrogram.class), ImageResize.nearest(image, 1));
  }
}
