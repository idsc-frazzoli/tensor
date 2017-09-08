// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Map;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;

/** {@link TensorMap} is equivalent to Mathematica::Map
 * but was given a different name in order not to conflict with {@link Map} */
public enum TensorMap {
  ;
  /** applies function to all entries of tensor at given level
   * 
   * <p>function obtains references to entries of tensor.
   * Modifications to the references are reflected in the input tensor.
   * 
   * @param function
   * @param tensor
   * @param level
   * @return */
  public static Tensor of(Function<Tensor, ? extends Tensor> function, Tensor tensor, int level) {
    if (0 < level)
      return Tensor.of(tensor.stream().map(entry -> of(function, entry, level - 1)));
    return function.apply(tensor);
  }
}
