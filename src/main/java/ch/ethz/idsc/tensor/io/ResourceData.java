// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.net.URL;

import ch.ethz.idsc.tensor.Tensor;

/** access to resource data included in the tensor library
 * 
 * <p>List of available resources:
 * <pre>
 * /colorscheme/classic.csv
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ResourceData.html">ResourceData</a> */
// EXPERIMENTAL API not finalized
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
      URL url = ResourceData.class.getResource(string);
      return Import.of(new File(url.getPath()));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
