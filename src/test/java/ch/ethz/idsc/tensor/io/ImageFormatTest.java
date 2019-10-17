// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class ImageFormatTest extends TestCase {
  public void testRGBAFile() throws Exception {
    Tensor tensor = TransposedImageFormatTest._readRGBA();
    String string = "/io/image/rgba15x33.png";
    File file = new File(getClass().getResource(string).getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor image = ImageFormat.from(bufferedImage);
    assertEquals(image, ResourceData.of(string));
    assertEquals(Transpose.of(tensor), image);
    // confirmed with gimp
    assertEquals(image.get(32, 0), Tensors.vector(126, 120, 94, 255));
  }

  public void testGray() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200);
    Tensor bimap = ImageFormat.from(ImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testColor() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200, 4);
    Tensor bimap = ImageFormat.from(ImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testGrayFile() throws Exception {
    String string = "/io/image/gray15x9.png";
    File file = new File(getClass().getResource(string).getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor tensor = ImageFormat.from(bufferedImage);
    assertEquals(tensor, ResourceData.of(string));
    // confirmed with gimp
    assertEquals(tensor.Get(0, 2), RealScalar.of(175));
    assertEquals(tensor.Get(1, 2), RealScalar.of(109));
    assertEquals(tensor.Get(2, 2), RealScalar.of(94));
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
  }

  public void testGrayscale() {
    Tensor tensor = Tensors.of(Range.of(0, 256));
    BufferedImage bufferedImage = ImageFormat.of(tensor);
    WritableRaster writableRaster = bufferedImage.getRaster();
    DataBuffer dataBuffer = writableRaster.getDataBuffer();
    DataBufferByte dataBufferByte = (DataBufferByte) dataBuffer;
    for (int index = 0; index < 256; ++index)
      assertEquals(dataBufferByte.getData()[index], (byte) index);
    int[] pixel = new int[1];
    for (int index = 0; index < 256; ++index) {
      bufferedImage.getRaster().getPixel(index, 0, pixel);
      assertEquals(index, pixel[0]);
    }
  }
}
