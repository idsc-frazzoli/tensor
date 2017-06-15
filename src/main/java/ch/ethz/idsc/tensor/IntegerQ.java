// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/IntegerQ.html">IntegerQ</a> */
public enum IntegerQ {
  ;
  /** @param tensor
   * @return true, if tensor is instance of {@link RationalScalar} with denominator == 1 */
  public static boolean of(Tensor tensor) {
    if (tensor instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) tensor;
      return rationalScalar.denominator().equals(BigInteger.ONE);
    }
    return false;
  }
}
