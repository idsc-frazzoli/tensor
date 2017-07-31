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
 * /colorscheme/hue.csv
 * /number/primes.vector
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ResourceData.html">ResourceData</a> */
public enum ResourceData {
  ;
  /** Example use:
   * Interpolation interpolation = LinearInterpolation.of(ResourceData.of("/colorscheme/classic.csv"));
   * 
   * @param string
   * @return imported tensor, or null if resource could not be loaded */
  public static Tensor of(String string) {
    InputStream inputStream = ResourceData.class.getResourceAsStream(string);
    if (inputStream == null)
      return null;
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      Filename filename = new Filename(new File(string));
      if (filename.hasExtension("csv"))
        return CsvFormat.parse(bufferedReader.lines());
      if (filename.hasExtension("vector"))
        return Tensor.of(bufferedReader.lines().map(Scalars::fromString));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
