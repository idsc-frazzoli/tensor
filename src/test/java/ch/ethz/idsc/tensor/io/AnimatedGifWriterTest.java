// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.utl.UserHome;
import junit.framework.TestCase;

public class AnimatedGifWriterTest extends TestCase {
  public void testColor() throws IOException {
    File file = UserHome.file("tensorLib_AnimatedGifWriterTest.gif");
    assertFalse(file.isFile());
    try (AnimatedGifWriter agw = AnimatedGifWriter.of(file, 100)) {
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
    }
    assertTrue(file.isFile());
    try (AnimatedGifWriter agw = AnimatedGifWriter.of(file, 120)) {
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB));
    }
    file.delete();
  }

  public void testGray() throws IOException {
    File file = UserHome.file("tensorLib_AnimatedGifWriterTest.gif");
    assertFalse(file.isFile());
    try (AnimatedGifWriter agw = AnimatedGifWriter.of(file, 100)) {
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_BYTE_GRAY));
      agw.append(new BufferedImage(2, 3, BufferedImage.TYPE_BYTE_GRAY));
    }
    file.delete();
  }

  public void testEmpty() throws IOException {
    File file = UserHome.file("tensorLib_AnimatedGifWriterTest.gif");
    assertFalse(file.isFile());
    try (AnimatedGifWriter agw = AnimatedGifWriter.of(file, 100)) {
      // ---
    }
    assertTrue(file.isFile());
    file.delete();
  }
}
