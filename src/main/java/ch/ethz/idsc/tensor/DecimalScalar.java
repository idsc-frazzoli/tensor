// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

// EXPERIMENTAL
// work in progress, use {@link DoubleScalar} instead
public class DecimalScalar extends AbstractRealScalar implements ChopInterface {
  // perhaps make this member
  private static final MathContext CONTEXT = MathContext.DECIMAL128;

  public static RealScalar of(BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) == 0 ? ZeroScalar.get() : new DecimalScalar(value);
  }

  public static RealScalar of(double value) {
    return of(BigDecimal.valueOf(value));
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

  @Override
  public Scalar negate() {
    return of(value.negate());
  }

  @Override
  public Scalar invert() {
    return of(BigDecimal.ONE.divide(value, CONTEXT));
  }

  @Override
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

  @Override
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

  @Override
  public Number number() {
    return value;
  }

  @Override
  protected boolean isNonNegative() {
    return 0 <= value.signum();
  }

  @Override
  public Scalar sqrt() {
    if (isNonNegative())
      return of(Sqrt.of(value));
    return ComplexScalar.of(ZeroScalar.get(), of(Sqrt.of(value.negate())));
  }

  @Override
  public Scalar chop(double threshold) {
    return value.abs().doubleValue() < threshold ? ZeroScalar.get() : this;
  }

  @Override
  public int compareTo(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return value.compareTo(decimalScalar.value);
    }
    if (scalar instanceof ZeroScalar)
      return signInt();
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) //
    (scalar instanceof NInterface ? ((NInterface) scalar).n() : scalar);
    return -comparable.compareTo(this);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) object;
      return value.equals(decimalScalar.value); // TODO check
    }
    if (object instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) object;
      return number().doubleValue() == realScalar.number().doubleValue();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
