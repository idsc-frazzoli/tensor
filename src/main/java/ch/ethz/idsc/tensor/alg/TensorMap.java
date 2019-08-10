// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Map;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;

/** TensorMap is equivalent to Mathematica::Map but was given a different name
 * in order not to conflict with {@link Map}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Map.html">Map</a> */
public enum TensorMap {
  ;
  /** applies function to all entries of tensor at given level
   * 
   * <p>function obtains references to entries of tensor.
   * Modifications to the references are reflected in the input tensor!
   * 
   * @param function
   * @param tensor
   * @param level non-negative
   * @return */
  public static Tensor of(Function<Tensor, ? extends Tensor> function, Tensor tensor, int level) {
    return 0 == level //
        ? function.apply(tensor)
        : _of(function, tensor, level);
  }

  // helper function
  private static Tensor _of(Function<Tensor, ? extends Tensor> function, Tensor tensor, int level) {
    return 1 == level //
        ? Tensor.of(tensor.stream().map(function))
        : Tensor.of(tensor.stream().map(entry -> _of(function, entry, level - 1)));
  }
}
