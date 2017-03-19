// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dot.html">Dot</a> */
public class Dot {
  /** @param m
   * @param v
   * @return m.v[0].v[1]...v[end] */
  public static Tensor of(Tensor tensor, Tensor... v) {
    if (v.length == 0)
      return tensor.copy();
    for (int index = 0; index < v.length; ++index)
      tensor = tensor.dot(v[index]);
    return tensor;
  }

  /** @param m
   * @param v
   * @return m.v[0].v[1]...v[end] */
  // experimental
  static Tensor asStream(Tensor... tensors) {
    // TODO not sure in which order the reduction takes place
    // for the code below to be valid it would have to be (((a1,a2),a3),a4),...
    return Arrays.asList(tensors).stream().reduce(Tensor::dot).orElse(Tensors.empty());
  }
}
