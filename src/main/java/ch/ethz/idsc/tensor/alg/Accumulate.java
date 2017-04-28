// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Accumulate.html">Accumulate</a> */
public enum Accumulate {
  ;
  // ---
  /** Accumulate[{a, b, c, d}] == {a, a + b, a + b + c, a + b + c + d}
   * 
   * @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    Tensor cumsum = Tensors.empty();
    if (tensor.length() == 0)
      return cumsum;
    Tensor entry = tensor.get(0);
    cumsum.append(entry);
    for (int index = 1; index < tensor.length(); ++index) {
      entry = entry.add(tensor.get(index));
      cumsum.append(entry);
    }
    return cumsum;
  }
}
