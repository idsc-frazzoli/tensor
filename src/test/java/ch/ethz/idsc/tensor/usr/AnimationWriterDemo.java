// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.AnimationWriter;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.utl.UserHome;

enum AnimationWriterDemo {
  ;
  public static void main(String[] args2) {
    try (AnimationWriter aw = AnimationWriter.of(UserHome.Pictures("grayscale.gif"), 100)) {
      for (int c = 1; c <= 16; ++c) {
        Distribution distribution = DiscreteUniformDistribution.of(0, c * 16);
        aw.append(RandomVariate.of(distribution, 128, 128));
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    try (AnimationWriter aw = AnimationWriter.of(UserHome.Pictures("colornoise.gif"), 100)) {
      for (int c = 1; c <= 16; ++c) {
        Distribution distribution = DiscreteUniformDistribution.of(0, c * 16);
        Tensor image = RandomVariate.of(distribution, 128, 128, 4);
        image.set(s -> RealScalar.of(255), Tensor.ALL, Tensor.ALL, 3);
        aw.append(image);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    try (AnimationWriter aw = AnimationWriter.of(UserHome.Pictures("palettenoise.gif"), 100)) {
      Distribution distribution = DiscreteUniformDistribution.of(0, 256);
      for (int c = 1; c <= 16; ++c)
        aw.append(ArrayPlot.of(RandomVariate.of(distribution, 128, 128), ColorDataGradients.ALPINE));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
