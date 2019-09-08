// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class ExtensionTest extends TestCase {
  public void testTruncate() {
    Filename filename = new Filename(new File("dir/some.bmp.gz"));
    assertEquals(filename.extension(), Extension.GZ);
    Filename truncate = filename.truncate();
    assertEquals(truncate.extension(), Extension.BMP);
  }

  public void testExtension() {
    Filename filename = new Filename(new File("dir/some.gif"));
    assertEquals(filename.extension(), Extension.GIF);
  }
}
