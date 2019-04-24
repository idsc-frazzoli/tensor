// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class DeleteDirectoryTest extends TestCase {
  public void testLayer0() throws IOException {
    File folder = HomeDirectory.Downloads(getClass().getSimpleName() + "0");
    folder.mkdir();
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 0, 1);
    assertEquals(deleteDirectory.deletedCount(), 1);
  }

  public void testLayer1a() throws IOException {
    File folder = HomeDirectory.Downloads(getClass().getSimpleName() + "1a");
    folder.mkdir();
    new File(folder, "sample1.txt").createNewFile();
    new File(folder, "sample2.txt").createNewFile();
    try {
      DeleteDirectory.of(folder, 0, 5);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      DeleteDirectory.of(folder, 1, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 1, 3);
    assertEquals(deleteDirectory.deletedCount(), 3);
  }

  public void testLayer1b() throws IOException {
    File folder = HomeDirectory.Downloads(getClass().getSimpleName() + "1b");
    folder.mkdir();
    new File(folder, "sample1.txt").createNewFile();
    new File(folder, "sample2.txt").createNewFile();
    File sub = new File(folder, "sub");
    sub.mkdir();
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 1, 5);
    assertEquals(deleteDirectory.deletedCount(), 4);
  }

  public void testLayer2() throws IOException {
    File folder = HomeDirectory.Downloads(getClass().getSimpleName() + "2");
    folder.mkdir();
    new File(folder, "sample1.txt").createNewFile();
    new File(folder, "sample2.txt").createNewFile();
    File sub = new File(folder, "sub");
    sub.mkdir();
    new File(sub, "content1.txt").createNewFile();
    try {
      DeleteDirectory.of(folder, 1, 10);
      fail();
    } catch (Exception exception) {
      // ---
    }
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 2, 5);
    assertEquals(deleteDirectory.deletedCount(), 5);
  }

  public void testNotFound() throws IOException {
    File folder = HomeDirectory.Downloads(getClass().getSimpleName() + "NotFound");
    try {
      DeleteDirectory.of(folder, 1, 10);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
