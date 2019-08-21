// code by jph
package ch.ethz.idsc.tensor.usr;

import java.util.concurrent.TimeUnit;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.AnimationWriter;
import ch.ethz.idsc.tensor.io.GifAnimationWriter;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;

/* package */ enum AnimationWriterDemo {
  ;
  public static void main(String[] args2) throws Exception {
    try (AnimationWriter animationWriter = //
        new GifAnimationWriter(HomeDirectory.Pictures("grayscale.gif"), 100, TimeUnit.MILLISECONDS)) {
      for (int count = 1; count <= 16; ++count) {
        Distribution distribution = DiscreteUniformDistribution.of(0, count * 16);
        animationWriter.write(RandomVariate.of(distribution, 128, 128));
      }
    }
    try (AnimationWriter animationWriter = //
        new GifAnimationWriter(HomeDirectory.Pictures("colornoise.gif"), 100, TimeUnit.MILLISECONDS)) {
      for (int count = 1; count <= 16; ++count) {
        Distribution distribution = DiscreteUniformDistribution.of(0, count * 16);
        Tensor image = RandomVariate.of(distribution, 128, 128, 4);
        image.set(s -> RealScalar.of(255), Tensor.ALL, Tensor.ALL, 3);
        animationWriter.write(image);
      }
    }
    try (AnimationWriter animationWriter = //
        new GifAnimationWriter(HomeDirectory.Pictures("palettenoise.gif"), 100, TimeUnit.MILLISECONDS)) {
      Distribution distribution = DiscreteUniformDistribution.of(0, 256);
      for (int count = 1; count <= 16; ++count)
        animationWriter.write(ArrayPlot.of(RandomVariate.of(distribution, 128, 128), ColorDataGradients.ALPINE));
    }
  }
}
