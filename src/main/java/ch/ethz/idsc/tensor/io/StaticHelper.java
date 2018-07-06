// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;

/* package */ enum StaticHelper {
  ;
  /** @param inputStream
   * @return lines in given inputStream as stream of strings */
  static Stream<String> lines(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream)).lines();
  }

  /** @param inputStream
   * @return tensor that is the result of parsing the lines in given inputStream as comma-separated values */
  static Tensor csv(InputStream inputStream) {
    return CsvFormat.parse(lines(inputStream));
  }
}
