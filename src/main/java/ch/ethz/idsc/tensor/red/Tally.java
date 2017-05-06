// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tally.html">Tally</a> */
public enum Tally {
  ;
  /** @param tensor
   * @return */
  public static Map<Tensor, Long> of(Tensor tensor) {
    return tensor.flatten(0).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }
}
