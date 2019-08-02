// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sort.html">Sort</a> */
public enum Sort {
  ;
  /** @param vector
   * @return vector with entries sorted according to canonic ordering
   * @see Ordering */
  public static Tensor of(Tensor vector) {
    if (vector.length() == 1)
      return vector.copy();
    return Tensor.of(vector.stream().sorted());
  }

  /** @param vector
   * @param comparator
   * @return tensor with entries sorted according to given comparator */
  @SuppressWarnings("unchecked")
  public static <T extends Scalar> Tensor of(Tensor vector, Comparator<T> comparator) {
    return Tensor.of(vector.stream().map(scalar -> (T) scalar).sorted(comparator));
  }

  /** Hint: the comparator is not allowed to alter the content of the parameters,
   * otherwise the behavior is undefined.
   * 
   * @param tensor
   * @param comparator
   * @return tensor with entries sorted according to given comparator */
  public static Tensor ofTensor(Tensor tensor, Comparator<Tensor> comparator) {
    return Tensor.of(tensor.stream().sorted(comparator).map(Tensor::copy));
  }
}
