// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import ch.ethz.idsc.tensor.sca.Chop;
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
 * @see N */
public final class DecimalScalar extends AbstractRealScalar implements //
    ChopInterface, NInterface {
  private static final MathContext DEFAULT_CONTEXT = MathContext.DECIMAL128;
  private static final Scalar DECIMAL_ZERO = of(BigDecimal.ZERO);
  private static final Scalar DECIMAL_PI = of(new BigDecimal(StaticHelper.N_PI_64, DEFAULT_CONTEXT));

  /** @param value
   * @return */
  public static Scalar of(BigDecimal value) {
    return new DecimalScalar(value);
  }

  /** @param value
   * @return scalar with value encoded as {@link BigDecimal#valueOf(double)} */
  public static Scalar of(double value) {
    return of(BigDecimal.valueOf(value));
  }

  /** @param value
   * @return scalar with value encoded as {@link BigDecimal#valueOf(long)} */
  public static Scalar of(long value) {
    return of(BigDecimal.valueOf(value));
  }

  /** @param string
   * @return scalar with value encoded as {@link BigDecimal(string)} */
  public static Scalar of(String string) {
    return of(new BigDecimal(string));
  }
  // ---

  private final BigDecimal value;

  private DecimalScalar(BigDecimal value) {
    this.value = value;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar negate() {
    return of(value.negate());
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(value.multiply(decimalScalar.value));
    }
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return of(value.multiply(rationalScalar.toBigDecimal(DEFAULT_CONTEXT)));
    }
    return scalar.multiply(this);
  }

  @Override // from Scalar
  public Scalar divide(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(value.divide(decimalScalar.value, mathContextHint()));
    }
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return of(value.divide(rationalScalar.toBigDecimal(DEFAULT_CONTEXT), mathContextHint()));
    }
    return scalar.under(this);
  }

  @Override // from Scalar
  public Scalar under(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(decimalScalar.value.divide(value, mathContextHint()));
    }
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return of(rationalScalar.toBigDecimal(DEFAULT_CONTEXT).divide(value, mathContextHint()));
    }
    return scalar.divide(this);
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return of(BigDecimal.ONE.divide(value, mathContextHint()));
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar zero() {
    return DECIMAL_ZERO;
  }

  private MathContext mathContextHint() {
    int precision = value.precision();
    // 34 is the precision of the DEFAULT_CONTEXT == MathContext.DECIMAL128
    return precision <= 34 ? DEFAULT_CONTEXT : new MathContext(precision, RoundingMode.HALF_EVEN);
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(value.add(decimalScalar.value));
    }
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return of(value.add(rationalScalar.toBigDecimal(mathContextHint())));
    }
    return scalar.add(this);
  }

  /***************************************************/
  @Override // from AbstractRealScalar
  public Scalar arg() {
    return isNonNegative() ? ZERO : DECIMAL_PI;
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return value.abs().doubleValue() < chop.threshold() ? ZERO : this;
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return RationalScalar.of(StaticHelper.ceiling(value), BigInteger.ONE);
  }

  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return value.compareTo(decimalScalar.value);
    }
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) N.in(mathContextHint()).apply(scalar);
    return -comparable.compareTo(this);
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return RationalScalar.of(StaticHelper.floor(value), BigInteger.ONE);
  }

  @Override // from ExpInterface
  public Scalar exp() {
    return of(BigDecimalMath.exp(value, mathContextHint()));
  }

  @Override // from NInterface
  public Scalar n() {
    // consistent with Mathematica: N[N[Pi, 50]] gives a machine number
    return DoubleScalar.of(number().doubleValue());
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    return of(value.round(mathContext));
  }

  @Override
  public Scalar power(Scalar exponent) {
    if (IntegerQ.of(exponent))
      try {
        // intValueExact throws an exception when exp > Integer.MAX_VALUE
        int expInt = Scalars.intValueExact(exponent);
        return of(value.pow(expInt, mathContextHint()));
      } catch (Exception exception) {
        // ---
      }
    return super.power(exponent);
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return RationalScalar.of(value.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact(), BigInteger.ONE);
  }

  @Override // from SignInterface
  public int signInt() {
    return value.signum();
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    if (isNonNegative())
      return of(BigDecimalMath.sqrt(value, mathContextHint()));
    return ComplexScalar.of(zero(), of(BigDecimalMath.sqrt(value.negate(), mathContextHint())));
  }

  @Override // from TrigonometryInterface
  public Scalar cos() {
    return of(BigDecimalMath.cos(value, mathContextHint()));
  }

  @Override // from TrigonometryInterface
  public Scalar cosh() {
    return of(BigDecimalMath.cosh(value, mathContextHint()));
  }

  @Override // from TrigonometryInterface
  public Scalar sin() {
    return of(BigDecimalMath.sin(value, mathContextHint()));
  }

  @Override // from TrigonometryInterface
  public Scalar sinh() {
    return of(BigDecimalMath.sinh(value, mathContextHint()));
  }

  /***************************************************/
  /** @return BigDecimal value stored by instance */
  public BigDecimal value() {
    return value;
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return value.hashCode();
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) object;
      // "equal() only if given BigDecimal's are equal in value and scale,
      // thus 2.0 is not equal to 2.00 when compared by equals()."
      return value.compareTo(decimalScalar.value) == 0;
    }
    if (object instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) object;
      BigDecimal bigDecimal = rationalScalar.toBigDecimal(mathContextHint());
      return value.compareTo(bigDecimal) == 0;
    }
    if (object instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) object;
      return number().doubleValue() == realScalar.number().doubleValue();
    }
    return Objects.nonNull(object) && object.equals(this);
  }

  /** BigDecimal precision of a double */
  private static final int DOUBLE_PRECISION = 17;

  @Override // from AbstractScalar
  public String toString() {
    int precision = value.precision();
    // TODO solution not elegant because result will be parsed as DoubleScalar
    return value.toString() + (precision <= DOUBLE_PRECISION ? "" : "`" + precision);
  }
}
