// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import junit.framework.TestCase;

public class HomeDirectoryTest extends TestCase {
  public void testUserHome() {
    assertTrue(HomeDirectory.file().isDirectory());
  }

  public void testNested() {
    File file = HomeDirectory.file("Doc", "proj1", "some.txt");
    assertFalse(file.toString().contains(" "));
  }

  public void testPictures() {
    assertTrue(HomeDirectory.Pictures().isDirectory());
  }

  public void testNullFail() {
    try {
      HomeDirectory.file("Doc", null, "some.txt");
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
