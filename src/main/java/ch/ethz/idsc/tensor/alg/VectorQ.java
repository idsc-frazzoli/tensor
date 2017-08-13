// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorQ.html">VectorQ</a> */
public enum VectorQ {
  ;
  /** @param tensor
   * @return true if tensor is a vector */
  public static boolean of(Tensor tensor) {
    try {
      return tensor.length() == 0 || Unprotect.dimension1(tensor) == Scalar.LENGTH;
    } catch (Exception exception) {
      // ---
    }
    return false;
  }

  /** @param tensor
   * @throws Exception if given tensor is not a vector */
  public static void orThrow(Tensor tensor) {
    if (tensor.length() != 0 && Unprotect.dimension1(tensor) != Scalar.LENGTH)
      throw TensorRuntimeException.of(tensor);
  }

  /** @param tensor
   * @param length
   * @return true if tensor is a vector with given length */
  public static boolean ofLength(Tensor tensor, int length) {
    return tensor.length() == length && of(tensor);
  }
}
