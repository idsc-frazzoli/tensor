// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class AnimationWriterTest extends TestCase {
  public void testColor() throws Exception {
    File file = HomeDirectory.file("tensorLib_AnimatedGifWriterTest.gif");
    assertFalse(file.exists());
    try (AnimationWriter animationWriter = AnimationWriter.of(file, 100)) {
      animationWriter.append(Array.zeros(3, 4));
      animationWriter.append(Array.zeros(3, 4));
    }
    assertTrue(file.delete());
  }

  public void testFailExtension() {
    try {
      AnimationWriter.of(new File("asd.bin"), 100); // extension unknown
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
