// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/** The purpose of the IntegerScalar enum is to provide static function that return instances of
 * {@link RationalScalar} with denominator 1. These scalars satisfy {@link IntegerQ}.
 * 
 * In particular, the class does not implement {@link Scalar} interface.
 * To prevent confusion the enum has package visibility and is used only in {@link Tensors}. */
/* package */ enum IntegerScalar {
  ;
  /** @param value
   * @return rational scalar encoding given integer value */
  public static Scalar of(BigInteger value) {
    return RationalScalar.of(value, BigInteger.ONE);
  }

  /** @param value
   * @return rational scalar encoding given integer value */
  public static Scalar of(long value) {
    return RationalScalar.of(value, 1);
  }
}
