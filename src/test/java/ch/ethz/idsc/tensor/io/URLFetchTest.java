// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class URLFetchTest extends TestCase {
  public void testSimple() throws MalformedURLException, IOException {
    if (UserName.is("travis")) {
      File file = TestFile.withExtension("ico");
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
        assertEquals(urlFetch.length(), 1406);
        assertEquals(urlFetch.contentType(), "image/x-icon");
        urlFetch.downloadIfMissing(file);
      }
      assertEquals(file.length(), 1406);
      file.delete();
      assertFalse(file.exists());
    }
  }

  public void testNoFileFail() throws MalformedURLException, IOException {
    if (UserName.is("travis"))
      try {
        try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/doesnotexist.file.unknown"))) {
          fail();
        }
      } catch (Exception exception) {
        // ---
      }
  }

  public void testInputStream() throws MalformedURLException, IOException {
    BufferedImage bufferedImage = null;
    if (UserName.is("travis"))
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/_images/icon.bik.png"))) {
        assertEquals(urlFetch.length(), 481);
        try (InputStream inputStream = urlFetch.inputStream()) {
          bufferedImage = ImageIO.read(inputStream);
        }
        assertEquals(bufferedImage.getHeight(), 16);
        assertEquals(bufferedImage.getWidth(), 16);
      }
  }

  public void testDuplicate() throws IOException {
    if (UserName.is("travis")) {
      File file = TestFile.withExtension("ico");
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
        urlFetch.downloadIfMissing(file);
        urlFetch.downloadIfMissing(file);
        try {
          urlFetch.download(HomeDirectory.Downloads("download.that.never.started"));
          fail();
        } catch (Exception exception) {
          // ---
        }
      }
      assertEquals(file.length(), 1406);
      file.delete();
      assertFalse(file.exists());
    }
  }
}
