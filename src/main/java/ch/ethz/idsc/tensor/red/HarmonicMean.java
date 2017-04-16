// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** implementation is consistent with Mathematica::HarmonicMean
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HarmonicMean.html">HarmonicMean</a> */
public enum HarmonicMean {
  ;
  /** computes the harmonic mean of the {@link Scalar}s on the first level of given tensor.
   * 
   * @param tensor is vector of non-zero scalars
   * @return harmonic mean of entries in tensor
   * @throws ArithmeticException if any entry of tensor is zero, or tensor is empty */
  public static Tensor of(Tensor tensor) {
    return RealScalar.of(tensor.length()).divide( //
        (Scalar) Total.of(tensor.map(Scalar::invert)));
  }
}
