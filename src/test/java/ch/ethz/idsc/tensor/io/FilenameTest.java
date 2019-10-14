// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class FilenameTest extends TestCase {
  public void testFailSpacing() {
    Filename filename = new Filename(new File("dir/title.bmp "));
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailExtension() {
    Filename filename = new Filename(new File("dir/title.ext"));
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNoExt() {
    Filename filename = new Filename(new File("dir/mybmp"));
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailTruncate() {
    Filename filename = new Filename(new File("dir/mybmp"));
    try {
      filename.truncate();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
