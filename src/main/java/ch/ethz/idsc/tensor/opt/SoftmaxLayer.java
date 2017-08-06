// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Exp;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SoftmaxLayer.html">SoftmaxLayer</a> */
public enum SoftmaxLayer {
  ;
  /** @param vector
   * @return */
  public static Tensor of(Tensor vector) {
    if (vector.length() == 0)
      throw TensorRuntimeException.of(vector);
    Tensor values = Exp.of(vector);
    return values.divide(Total.of(values).Get());
  }
}
