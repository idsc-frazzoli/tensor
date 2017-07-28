// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** a decimal scalar encodes a number as {@link BigDecimal}
 * 
 * <p>{@link DecimalScalar} offers increased precision over {@link DoubleScalar} */
public final class DecimalScalar extends AbstractRealScalar implements ChopInterface {
  private static final Scalar DECIMAL_ZERO = of(BigDecimal.ZERO);
  // perhaps make this member
  private static final MathContext CONTEXT = MathContext.DECIMAL128;

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

  /** @param string
   * @return scalar with value encoded as {@link BigDecimal(string)} */
  public static Scalar of(String string) {
    return of(new BigDecimal(string));
  }

  static BigDecimal approx(RationalScalar rationalScalar) {
    BigDecimal num = new BigDecimal(rationalScalar.numerator());
    BigDecimal den = new BigDecimal(rationalScalar.denominator());
    return num.divide(den, CONTEXT);
  }

  private final BigDecimal value;

  private DecimalScalar(BigDecimal value) {
    this.value = value;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar invert() {
    return of(BigDecimal.ONE.divide(value, CONTEXT));
  }

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
      return of(value.multiply(approx(rationalScalar)));
    }
    return scalar.multiply(this);
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar zero() {
    return DECIMAL_ZERO;
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
      return of(value.add(approx(rationalScalar)));
    }
    return scalar.add(this);
  }

  /***************************************************/
  @Override // from AbstractRealScalar
  public Scalar arg() {
    // TODO perhaps return better precision
    return super.arg();
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
    Comparable<Scalar> comparable = (Comparable<Scalar>) //
    (scalar instanceof NInterface ? ((NInterface) scalar).n() : scalar);
    return -comparable.compareTo(this);
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return RationalScalar.of(StaticHelper.floor(value), BigInteger.ONE);
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
      return of(Sqrt.of(value));
    return ComplexScalar.of(zero(), of(Sqrt.of(value.negate())));
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
    if (object instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) object;
      return number().doubleValue() == realScalar.number().doubleValue();
    }
    return object == null ? false : object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    return value.toString();
  }
}
