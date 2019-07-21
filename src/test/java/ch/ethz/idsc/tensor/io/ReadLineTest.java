// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ReadLineTest extends TestCase {
  public void testParse() throws IOException {
    try (Stream<String> stream = ReadLine.of(getClass().getResource("/io/libreoffice_calc.csv").openStream())) {
      Tensor table = CsvFormat.parse(stream);
      assertEquals(Dimensions.of(table), Arrays.asList(4, 2));
    }
  }

  public void testCount2() throws IOException {
    try (InputStream inputStream = getClass().getResource("/io/libreoffice_calc.csv").openStream()) {
      try (Stream<String> stream = ReadLine.of(inputStream)) {
        Tensor table = CsvFormat.parse(stream);
        assertEquals(Dimensions.of(table), Arrays.asList(4, 2));
      }
      assertEquals(inputStream.available(), 0);
    }
  }

  public void testCount() throws IOException {
    try (InputStream inputStream = getClass().getResource("/io/libreoffice_calc.csv").openStream()) {
      long count = ReadLine.of(inputStream).count();
      assertEquals(count, 4);
      assertEquals(inputStream.available(), 0);
      inputStream.close();
      try {
        inputStream.available();
        fail();
      } catch (Exception exception) {
        // ---
      }
    }
  }

  public void testFail() throws IOException {
    try (Stream<String> stream = ReadLine.of(getClass().getResource("/io/doesnotexist.csv").openStream())) {
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
