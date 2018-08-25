// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** implementation is consistent with Mathematica:
 * <pre>
 * Diagonal[{{{0}, 1}, {2, 3}, {4, 5}}] == {{0}, 3}
 * Diagonal[{1, 2, 3, 4}] => {}
 * </pre>
 * 
 * For Scalar input, the result is undefined
 * <pre>
 * Diagonal[3] => Exception
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Diagonal.html">Diagonal</a> */
public enum Diagonal {
  ;
  /** @param tensor
   * @return vector of entries on diagonal of given tensor
   * @throws Exception if tensor is a scalar */
  public static Tensor of(Tensor tensor) {
    return Tensors.vector(i -> tensor.get(i, i), Math.min(tensor.length(), Unprotect.dimension1(tensor)));
  }
}
