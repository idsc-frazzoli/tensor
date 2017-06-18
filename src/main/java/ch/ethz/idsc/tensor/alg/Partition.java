// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** Partition is used in {@link Transpose}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Partition.html">Partition</a> */
public enum Partition {
  ;
  /** Mathematica's Partition[{a, b, c, d, e, f}, 2] == {{a, b}, {c, d}, {e, f}}
   * 
   * @param tensor
   * @param size has to divide tensor.length()
   * @return a tensor of rank one higher than input tensor */
  public static Tensor of(Tensor tensor, int size) {
    List<Tensor> parts = new ArrayList<>();
    for (int index = 0; index < tensor.length(); index += size)
      parts.add(tensor.extract(index, index + size));
    return Tensor.of(parts.stream());
  }
}
