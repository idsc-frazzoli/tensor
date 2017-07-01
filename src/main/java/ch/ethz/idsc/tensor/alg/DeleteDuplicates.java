// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DeleteDuplicates.html">DeleteDuplicates</a> */
public enum DeleteDuplicates {
  ;
  public static Tensor of(Tensor tensor) {
    return Tensor.of(tensor.flatten(0).distinct());
  }
}
