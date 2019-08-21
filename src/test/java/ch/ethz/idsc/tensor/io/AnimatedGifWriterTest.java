// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class AnimatedGifWriterTest extends TestCase {
  public void testColor() throws IOException {
    File file = TestFile.withExtension("gif");
    try (AnimatedGifWriter animatedGifWriter = AnimatedGifWriter.of(file, 100)) {
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
    }
    assertTrue(file.isFile());
    try (AnimatedGifWriter animatedGifWriter = AnimatedGifWriter.of(file, 120)) {
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
    }
    assertTrue(file.delete());
  }

  public void testGray() throws IOException {
    File file = TestFile.withExtension("gif");
    try (AnimatedGifWriter animatedGifWriter = AnimatedGifWriter.of(file, 100)) {
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_BYTE_GRAY));
      animatedGifWriter.write(new BufferedImage(2, 3, BufferedImage.TYPE_BYTE_GRAY));
    }
    assertTrue(file.delete());
  }

  public void testEmpty() throws IOException {
    File file = TestFile.withExtension("gif");
    try (AnimatedGifWriter animatedGifWriter = AnimatedGifWriter.of(file, 100)) {
      // ---
    }
    assertTrue(file.isFile());
    assertTrue(file.delete());
  }
}
