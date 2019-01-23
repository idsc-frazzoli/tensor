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

  public void testDesktop() {
    assertTrue(HomeDirectory.Desktop().isDirectory());
    assertEquals(HomeDirectory.Desktop(), HomeDirectory.file("Desktop"));
    assertEquals(HomeDirectory.Desktop("test.ico"), HomeDirectory.file("Desktop", "test.ico"));
  }

  public void testDocuments() {
    assertTrue(HomeDirectory.Documents().isDirectory());
    assertEquals(HomeDirectory.Documents(), HomeDirectory.file("Documents"));
    assertEquals(HomeDirectory.Documents("test.txt"), HomeDirectory.file("Documents", "test.txt"));
  }

  public void testDownloads() {
    assertTrue(HomeDirectory.Downloads().isDirectory());
    assertEquals(HomeDirectory.Downloads(), HomeDirectory.file("Downloads"));
    assertEquals(HomeDirectory.Downloads("test.txt"), HomeDirectory.file("Downloads", "test.txt"));
  }

  public void testPictures() {
    assertTrue(HomeDirectory.Pictures().isDirectory());
    assertEquals(HomeDirectory.Pictures(), HomeDirectory.file("Pictures"));
    assertEquals(HomeDirectory.Pictures("test.png"), HomeDirectory.file("Pictures", "test.png"));
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
