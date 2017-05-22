// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;

import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.ImagInterface;
import ch.ethz.idsc.tensor.sca.RealInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** RealScalar encodes a real number
 * 
 * <p>encodings provided by the tensor library are
 * <ul>
 * <li>integer fraction {@link RationalScalar}
 * <li>decimal with double precision {@link DoubleScalar}
 * <li>decimal with extra precision {@link DecimalScalar}
 * </ul> */
public interface RealScalar extends Scalar, //
    ArgInterface, ConjugateInterface, Comparable<Scalar>, ImagInterface, //
    RealInterface, SqrtInterface {
  /** real scalar 1 as a {@link RationalScalar} */
  static final RealScalar ONE = RealScalar.of(1);
  /** real scalar that encodes Infinity. value is backed by Double.POSITIVE_INFINITY */
  static final RealScalar POSITIVE_INFINITY = of(Double.POSITIVE_INFINITY);
  /** real scalar that encodes -Infinity. value is backed by Double.NEGATIVE_INFINITY */
  static final RealScalar NEGATIVE_INFINITY = of(Double.NEGATIVE_INFINITY);

  /** @param number
   * @return scalar with best possible accuracy to describe number */
  static RealScalar of(Number number) {
    if (number instanceof Integer || number instanceof Long)
      return RationalScalar.of(number.longValue(), 1);
    if (number instanceof Float || number instanceof Double)
      return DoubleScalar.of(number.doubleValue());
    if (number instanceof BigInteger)
      return RationalScalar.of((BigInteger) number, BigInteger.ONE);
    if (number instanceof BigDecimal)
      return DecimalScalar.of((BigDecimal) number);
    throw new IllegalArgumentException("" + number);
  }

  /***************************************************/
  /** @return gives -1, 0, or 1 depending on whether this is negative, zero, or positive. */
  int signInt();
}
