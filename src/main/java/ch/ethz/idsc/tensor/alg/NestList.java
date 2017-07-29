// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NestList.html">NestList</a> */
public enum NestList {
  ;
  /** gives a list of the results of applying f to x 0 through n times
   * 
   * @param function
   * @param x
   * @param n
   * @return */
  public static Tensor of(Function<Tensor, Tensor> function, Tensor x, int n) {
    Tensor tensor = Tensors.of(x);
    for (int index = 0; index < n; ++index)
      tensor.append(function.apply(Last.of(tensor)));
    return tensor;
  }
}
