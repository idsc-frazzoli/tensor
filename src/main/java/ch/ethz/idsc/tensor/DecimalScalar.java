// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;

import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;

/** a decimal scalar encodes a number as {@link BigDecimal}.
 * Unless the precision is explicitly specified, MathContext.DECIMAL128 is used.
 * In particular, {@link DecimalScalar} offers increased precision over {@link DoubleScalar}.
 * 
 * <p>The string representation of a {@link DecimalScalar} is of the form
 * {@code [decimal]`[precision]}. Examples are
 * <pre>
 * 220255.6579480671651695790064528423`34
 * 1.414213562373095048801688724209698`34
 * -0.37840124765396412568631954725591454706`19.69897000433602
 * </pre>
 * [precision] denotes how many digits from left to right are correct.
 * The pattern is consistent with Mathematica.
 * 
 * @see N
 * @see Pi */
public interface DecimalScalar extends RealScalar, ChopInterface, NInterface {
  /** @param value
   * @return */
  static Scalar of(BigDecimal value) {
    return DecimalScalarImpl.of(value);
  }

  /** @param value
   * @return scalar with value encoded as {@link BigDecimal#valueOf(double)} */
  static Scalar of(double value) {
    return of(BigDecimal.valueOf(value));
  }

  /** @param value
   * @return scalar with value encoded as {@link BigDecimal#valueOf(long)} */
  static Scalar of(long value) {
    return of(BigDecimal.valueOf(value));
  }

  /** @param string
   * @return scalar with value encoded as {@link BigDecimal(string)} */
  static Scalar of(String string) {
    return of(new BigDecimal(string));
  }

  /** @param string
   * @param precision
   * @return scalar with value encoded as {@link BigDecimal(string)} */
  static Scalar of(String string, int precision) {
    return new DecimalScalarImpl(new BigDecimal(string), precision);
  }

  /***************************************************/
  @Override // from Scalar
  BigDecimal number();

  /** @return */
  int precision();
}
