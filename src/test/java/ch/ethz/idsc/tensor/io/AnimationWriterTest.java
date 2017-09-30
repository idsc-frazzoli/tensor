// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class AnimationWriterTest extends TestCase {
  public void testSimple() {
    try {
      AnimationWriter.of(new File("asd.bin"), 100);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
