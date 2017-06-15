// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Accumulate.html">Accumulate</a> */
public enum Accumulate {
  ;
  /** Accumulate.of[{a, b, c, d}] == {a, a + b, a + b + c, a + b + c + d}
   * 
   * <p>concept as Matlab::cumsum
   * 
   * @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    return FoldList.of(Tensor::add, tensor);
  }

  /** Accumulate.prod[{a, b, c, d}] == {a, a * b, a * b * c, a * b * c * d}
   * 
   * <p>concept as Matlab::cumprod
   * 
   * @param tensor
   * @return */
  public static Tensor prod(Tensor tensor) {
    return FoldList.of(Tensor::pmul, tensor);
  }
}
