// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dot.html">Dot</a> */
public enum Dot {
  ;
  /** @param tensor
   * @param v's
   * @return ( ... ( ( m . v[0] ) . v[1] ). ... ) . v[end-1] */
  public static Tensor of(Tensor tensor, Tensor... v) {
    if (v.length == 0)
      return tensor.copy();
    for (int index = 0; index < v.length; ++index)
      tensor = tensor.dot(v[index]);
    return tensor;
  }
}
