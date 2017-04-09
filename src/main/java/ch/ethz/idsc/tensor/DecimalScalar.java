// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;

//TODO work in progress, use {@link DoubleScalar} instead
class DecimalScalar extends AbstractRealScalar {
  public static Scalar of(BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) == 0 ? ZeroScalar.get() : new DecimalScalar(value);
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
    return of(BigDecimal.ONE.divide(value));
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(value.add(decimalScalar.value));
    }
    return null; // FIXME
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return of(value.multiply(decimalScalar.value));
    }
    return null; // FIXME
  }

  @Override
  public Number number() {
    return value;
  }

  @Override
  public Scalar n() {
    return this;
  }

  @Override
  protected boolean isNonNegative() {
    return 0 <= value.signum();
  }

  @Override
  public int compareTo(Scalar scalar) {
    if (scalar instanceof DecimalScalar) {
      DecimalScalar decimalScalar = (DecimalScalar) scalar;
      return value.compareTo(decimalScalar.value);
    }
    // FIXME
    return 0;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    // FIXME
    return false;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
