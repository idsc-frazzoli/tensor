// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/RotateLeft.html">RotateLeft</a> */
public enum RotateLeft {
  ;
  /** RotateLeft[{a, b, c, d, e}, 2] == {c, d, e, a, b}
   * 
   * @param tensor
   * @param n
   * @return */
  public static Tensor of(Tensor tensor, int n) {
    int index = n % tensor.length();
    if (index < 0)
      index += tensor.length();
    return Join.of( //
        tensor.extract(index, tensor.length()), //
        tensor.extract(0, index));
  }
}
