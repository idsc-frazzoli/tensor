// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.util.concurrent.TimeUnit;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class AnimationWriterTest extends TestCase {
  public void testColor() throws Exception {
    File file = TestFile.withExtension("gif");
    try (AnimationWriter animationWriter = new GifAnimationWriter(file, 100, TimeUnit.MILLISECONDS)) {
      animationWriter.append(Array.zeros(3, 4));
      animationWriter.append(Array.zeros(3, 4));
    }
    assertTrue(file.isFile());
    assertTrue(file.delete());
  }

  public void testFailExtension() {
    try (AnimationWriter animationWriter = new GifAnimationWriter(null, 100, TimeUnit.MILLISECONDS)) { // extension unknown
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
