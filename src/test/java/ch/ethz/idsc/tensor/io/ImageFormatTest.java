// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class ImageFormatTest extends TestCase {
  public void testRGBAFile() throws Exception {
    Tensor tensor = TransposedImageFormatTest._readRGBA();
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor nif = ImageFormat.from(bufferedImage);
    assertEquals(Transpose.of(tensor), nif);
    // confirmed with gimp
    assertEquals(nif.get(32, 0), Tensors.vector(126, 120, 94, 255));
  }

  public void testSimpleGray() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200);
    Tensor bimap = ImageFormat.fromGrayscale(ImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testSimpleColor() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200, 4);
    Tensor bimap = ImageFormat.from(ImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testGrayFile() throws Exception {
    File file = new File(getClass().getResource("/io/gray15x9.png").getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor tensor = ImageFormat.fromGrayscale(bufferedImage);
    // confirmed with gimp
    assertEquals(tensor.Get(0, 2), RealScalar.of(175));
    assertEquals(tensor.Get(1, 2), RealScalar.of(109));
    assertEquals(tensor.Get(2, 2), RealScalar.of(94));
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
  }
}
