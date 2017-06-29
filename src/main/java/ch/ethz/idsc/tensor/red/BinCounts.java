// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BinCounts.html">BinCounts</a> */
// EXPERIMENTAL
/* package */ enum BinCounts {
  ;
  /** @param vector
   * @param function
   * @return */
  // TODO API not finalized
  public static Tensor of(Tensor vector, Function<Scalar, Scalar> function) {
    Map<Tensor, Long> map = Tally.of(vector.map(function)); // TODO use sorted
    Tensor tensor = Tensors.empty();
    for (Entry<Tensor, Long> entry : map.entrySet())
      tensor.append(Tensors.of(entry.getKey(), RealScalar.of(entry.getValue())));
    return tensor;
  }
}
