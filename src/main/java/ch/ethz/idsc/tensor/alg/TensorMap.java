// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;

// TODO comment and evaluate, like Mathematica::Map, but here different name in order to not to conflict
public class TensorMap {
  public static Tensor of(Function<Tensor, Tensor> function, Tensor tensor, int level) {
    if (0 < level)
      return Tensor.of(tensor.flatten(0).map(entry -> of(function, entry, level - 1)));
    return function.apply(tensor);
  }
}
