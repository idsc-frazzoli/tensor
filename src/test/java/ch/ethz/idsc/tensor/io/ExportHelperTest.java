// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExportHelperTest extends TestCase {
  public void testGif() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
    Tensor image = Tensors.fromString("{{{1,2,3,255},{91,120,230,255}}}");
    ExportHelper.of(Extension.GIF, image, outputStream);
    byte[] array = outputStream.toByteArray();
    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(array));
    Tensor tensor = ImageFormat.from(bufferedImage);
    assertEquals(image, tensor);
  }

  public void testFileExtensionFail() {
    OutputStream outputStream = new ByteArrayOutputStream(512);
    try {
      ExportHelper.of(Extension.VECTOR, Tensors.empty(), outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testGzFail() {
    OutputStream outputStream = new ByteArrayOutputStream(512);
    try {
      ExportHelper.of(Extension.GZ, Tensors.empty(), outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
