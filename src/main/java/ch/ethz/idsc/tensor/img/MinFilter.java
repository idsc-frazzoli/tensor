// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.red.Entrywise;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MinFilter.html">MinFilter</a> */
public enum MinFilter {
  ;
  /** @param tensor
   * @param radius
   * @return */
  public static Tensor of(Tensor tensor, int radius) {
    return TensorExtract.of(Unprotect.references(tensor), radius, Entrywise.min()::of);
  }
}
