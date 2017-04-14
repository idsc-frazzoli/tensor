// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** One application of {@link ListConvolve} is the computation of the coefficients
 * of the product of two polynomials.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListConvolve.html">ListConvolve</a> */
public enum ListConvolve {
  ;
  // ---
  /** ListConvolve[{x, y}, {a, b, c, d, e, f}] ==
   * {b x + a y, c x + b y, d x + c y, e x + d y, f x + e y}
   * 
   * @param kernel
   * @param tensor
   * @return convolution of kernel with tensor */
  public static Tensor of(Tensor kernel, Tensor tensor) {
    return ListCorrelate.of(Reverse.all(kernel), tensor);
  }
}
