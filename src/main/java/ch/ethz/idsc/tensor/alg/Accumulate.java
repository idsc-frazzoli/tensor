// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Accumulate.html">Accumulate</a> */
public enum Accumulate {
  ;
  /** <pre>
   * Accumulate.of[{a, b, c, d}] == {a, a + b, a + b + c, a + b + c + d}
   * </pre>
   * 
   * <p>concept as Matlab::cumsum
   * 
   * @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    return FoldList.of(Tensor::add, tensor);
  }

  /** <pre>
   * Accumulate.prod[{a, b, c, d}] == {a, a * b, a * b * c, a * b * c * d}
   * </pre>
   * 
   * <p>concept as Matlab::cumprod
   * 
   * @param tensor
   * @return */
  public static Tensor prod(Tensor tensor) {
    return FoldList.of(Tensor::pmul, tensor);
  }
}
