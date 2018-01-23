// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Rescale;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  /** Hint: the function replaces scalar entries of given tensor with 4-vector that
   * encodes RGBA color values. if the input tensor does not have matrix structure,
   * the return tensor cannot be exported to an image.
   * 
   * @param tensor, typically a matrix
   * @param colorDataFunction, for instance ColorDataGradients.CLASSIC
   * @return */
  public static Tensor of(Tensor tensor, ColorDataFunction colorDataFunction) {
    return Rescale.of(tensor).map(colorDataFunction);
  }
}
