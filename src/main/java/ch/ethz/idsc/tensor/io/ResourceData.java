// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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
      return Import.of(new File(uri(string).getPath()));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  /** @param string
   * @return url */
  // EXPERIMENTAL API not finalized
  public static URL url(String string) {
    // jar:file:/home/datahaki/.m2/repository/ch/ethz/idsc/tensor/0.2.7/tensor-0.2.7.jar!/colorscheme/classic.csv
    return ResourceData.class.getResource(string);
  }

  /** @param string
   * @return uri
   * @throws URISyntaxException */
  // EXPERIMENTAL API not finalized
  public static URI uri(String string) throws URISyntaxException {
    return url(string).toURI();
  }
}
