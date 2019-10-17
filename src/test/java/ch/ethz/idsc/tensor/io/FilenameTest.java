// code by jph
package ch.ethz.idsc.tensor.io;

import junit.framework.TestCase;

public class FilenameTest extends TestCase {
  public void testFailSpacing() {
    Filename filename = new Filename("dir/title.bmp ");
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailExtension() {
    Filename filename = new Filename("dir/title.ext");
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNoExt() {
    Filename filename = new Filename("dir/mybmp");
    try {
      filename.extension();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailTruncate() {
    Filename filename = new Filename("dir/mybmp");
    try {
      filename.truncate();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
