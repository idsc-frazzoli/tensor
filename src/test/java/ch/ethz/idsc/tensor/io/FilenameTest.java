// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class FilenameTest extends TestCase {
  public void testExtension() {
    Filename filename = new Filename(new File("dir/title.ext"));
    for (Extension extension : Extension.values())
      assertFalse(filename.has(extension));
  }

  public void testNoExt() {
    Filename filename = new Filename(new File("dir/mybmp"));
    for (Extension extension : Extension.values())
      assertFalse(filename.has(extension));
  }
}
