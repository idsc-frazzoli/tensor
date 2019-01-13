// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.AnimationWriter;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;

/* package */ enum AnimationWriterDemo {
  ;
  public static void main(String[] args2) {
    try (AnimationWriter animationWriter = AnimationWriter.of(HomeDirectory.Pictures("grayscale.gif"), 100)) {
      for (int count = 1; count <= 16; ++count) {
        Distribution distribution = DiscreteUniformDistribution.of(0, count * 16);
        animationWriter.append(RandomVariate.of(distribution, 128, 128));
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    try (AnimationWriter animationWriter = AnimationWriter.of(HomeDirectory.Pictures("colornoise.gif"), 100)) {
      for (int count = 1; count <= 16; ++count) {
        Distribution distribution = DiscreteUniformDistribution.of(0, count * 16);
        Tensor image = RandomVariate.of(distribution, 128, 128, 4);
        image.set(s -> RealScalar.of(255), Tensor.ALL, Tensor.ALL, 3);
        animationWriter.append(image);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    try (AnimationWriter animationWriter = AnimationWriter.of(HomeDirectory.Pictures("palettenoise.gif"), 100)) {
      Distribution distribution = DiscreteUniformDistribution.of(0, 256);
      for (int count = 1; count <= 16; ++count)
        animationWriter.append(ArrayPlot.of(RandomVariate.of(distribution, 128, 128), ColorDataGradients.ALPINE));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
