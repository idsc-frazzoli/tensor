// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Rescale;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  /** @param tensor
   * @param colorDataFunction
   * @return */
  public static Tensor of(Tensor tensor, ColorDataFunction colorDataFunction) {
    return Rescale.of(tensor).map(colorDataFunction);
  }
}
