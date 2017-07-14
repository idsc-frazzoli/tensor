// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** access to resource data included in the tensor library
 * 
 * <p>List of available resources:
 * <pre>
 * /colorscheme/classic.csv
 * /number/primes.vector
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ResourceData.html">ResourceData</a> */
public enum ResourceData {
  ;
  /** Example use:
   * Tensor tensor = ResourceData.of("/colorscheme/classic.csv");
   * Interpolation interpolation = LinearInterpolation.of(tensor);
   * 
   * @param string
   * @return imported tensor, or null if resource could not be loaded */
  public static Tensor of(String string) {
    try {
      Filename filename = new Filename(new File(string));
      InputStream inputStream = ResourceData.class.getResourceAsStream(string);
      if (filename.hasExtension("csv"))
        return _csv(inputStream);
      if (filename.hasExtension("vector"))
        return _vector(inputStream);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  // helper function to read csv from stream
  private static Tensor _csv(InputStream inputStream) throws Exception {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      return CsvFormat.parse(bufferedReader.lines());
    }
  }

  private static Tensor _vector(InputStream inputStream) throws Exception {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      return Tensor.of(bufferedReader.lines().map(Scalars::fromString));
    }
  }
}
