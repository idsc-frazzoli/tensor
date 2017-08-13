// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** consistent with Mathematica
 * <pre>
 * Diagonal[{{{0}, 1}, {2, 3}, {4, 5}}] == {{0}, 3}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Diagonal.html">Diagonal</a> */
public enum Diagonal {
  ;
  /** @param tensor
   * @return vector of entries on diagonal of given tensor */
  public static Tensor of(Tensor tensor) {
    return Tensors.vector(i -> tensor.get(i, i), Math.min(tensor.length(), Unprotect.length0(tensor)));
  }
}
