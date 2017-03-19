// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sort.html">Sort</a> */
public class Sort {
  /** @param tensor
   * @return tensor with entries sorted according to canonic ordering */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(tensor.flatten(0).sorted());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tensor> Tensor of(Tensor tensor, Comparator<T> comparator) {
    return Tensor.of(tensor.flatten(0).map(x -> (T) x).sorted(comparator));
  }
}
