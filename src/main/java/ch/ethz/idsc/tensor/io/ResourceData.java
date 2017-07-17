// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** access to resource data included in the tensor library
 * 
 * <p>List of available resources:
 * <pre>
 * /colorscheme/classic.csv
 * TODO /colorscheme/hue.csv
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
   * @return imported tensor, or null if resource could not be loaded
   * @throws IOException */
  public static Tensor of(String string) throws IOException {
    Filename filename = new Filename(new File(string));
    InputStream inputStream = ResourceData.class.getResourceAsStream(string);
    if (inputStream == null)
      throw new IOException(string); // can't open resource
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      if (filename.hasExtension("csv"))
        return CsvFormat.parse(bufferedReader.lines());
      if (filename.hasExtension("vector"))
        return Tensor.of(bufferedReader.lines().map(Scalars::fromString));
    }
    return null;
  }
}
