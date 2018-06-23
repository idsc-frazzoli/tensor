// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class FilenameTest extends TestCase {
  public void testExtension() {
    Filename filename = new Filename(new File("dir/title.ext"));
    assertEquals(filename.extension, "ext");
    assertEquals(filename.title, "title");
    assertTrue(filename.hasExtension("ext"));
  }

  public void testNoExt() {
    Filename filename = new Filename(new File("dir/title"));
    assertEquals(filename.extension, "");
    assertEquals(filename.title, "title");
    assertTrue(filename.hasExtension(""));
  }

  public void testExt() {
    Filename filename = new Filename(new File("dir/title.ext"));
    File file = filename.withExtension("txt");
    assertEquals(file.toString(), "dir/title.txt");
  }

  public void testExtNull() {
    Filename filename = new Filename(new File("dir/title.ext"));
    File file = filename.withExtension(null);
    assertEquals(file.toString(), "dir/title");
  }
}
