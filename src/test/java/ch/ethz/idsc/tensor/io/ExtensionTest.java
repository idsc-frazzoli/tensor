// code by jph
package ch.ethz.idsc.tensor.io;

import junit.framework.TestCase;

public class ExtensionTest extends TestCase {
  public void testTruncate() {
    Filename filename = new Filename("dir/some.bmp.gz");
    assertEquals(filename.extension(), Extension.GZ);
    Filename truncate = filename.truncate();
    assertEquals(truncate.extension(), Extension.BMP);
  }

  public void testExtension() {
    Filename filename = new Filename("dir/some.gif");
    assertEquals(filename.extension(), Extension.GIF);
  }

  public void testSimple() {
    assertEquals(Extension.of("bMp"), Extension.BMP);
    assertEquals(Extension.of("gz"), Extension.GZ);
  }

  public void testFail() {
    try {
      Extension.of("unknown");
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
