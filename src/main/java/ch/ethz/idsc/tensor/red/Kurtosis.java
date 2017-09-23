// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Kurtosis.html">Kurtosis</a> */
/* package */ enum Kurtosis {
  ;
  public static Scalar of(Tensor tensor) {
    // TODO implement
    throw TensorRuntimeException.of(tensor);
  }
}
