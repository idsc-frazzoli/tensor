// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sort.html">Sort</a> */
public enum Sort {
  ;
  /** @param tensor
   * @return tensor with entries sorted according to canonic ordering */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(tensor.stream().sorted());
  }

  /** @param tensor
   * @param comparator
   * @return tensor with entries sorted according to given comparator */
  public static Tensor of(Tensor tensor, Comparator<? super Tensor> comparator) {
    return Tensor.of(tensor.stream().sorted(comparator));
  }
}
