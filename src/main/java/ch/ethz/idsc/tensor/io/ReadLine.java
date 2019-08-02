// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ReadLine.html">ReadLine</a> */
public enum ReadLine {
  ;
  /** Hint: even after completion of a terminal operation on the returned {@link Stream}
   * the given {@link InputStream} is not necessarily closed. Therefore, use
   * try-with-resources statement on input stream.
   * 
   * Example:
   * <pre>
   * try (InputStream inputStream = new FileInputStream(file)) {
   * . ReadLine.of(inputStream).map(...).forEach(...)
   * }
   * </pre>
   * 
   * @param inputStream
   * @return lines in given inputStream as stream of strings */
  public static Stream<String> of(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream)).lines();
  }
}
