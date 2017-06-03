// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

public class QuantityScalar extends AbstractScalar {
  public static Scalar of(Scalar value, String unit, Scalar exponent) {
    return new QuantityScalar(value, unit, exponent);
  }

  private final Scalar value;
  private final String unit;
  private final Scalar exponent;

  private QuantityScalar(Scalar value, String unit, Scalar exponent) {
    this.value = value;
    this.unit = unit;
    this.exponent = exponent;
  }

  @Override
  public Scalar negate() {
    return of(value.negate(), unit, exponent);
  }

  @Override
  public Scalar invert() {
    return of(value.invert(), unit, exponent.invert());
  }

  @Override
  public Scalar abs() {
    return of(value.abs(), unit, exponent);
  }

  @Override
  public Number number() {
    return value.number();
  }

  @Override
  public Scalar zero() {
    return of(value.zero(), unit, exponent);
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      if (unit.equals(quantityScalar.unit) && exponent.equals(quantityScalar.exponent))
        return of(value.add(quantityScalar.value), unit, exponent);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      if (unit.equals(quantityScalar.unit))
        return of(value.multiply(quantityScalar.value), unit, exponent.multiply(quantityScalar.exponent));
      throw TensorRuntimeException.of(this, scalar);
    }
    return of(value.multiply(scalar), unit, exponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unit, exponent);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) object;
      return value.equals(quantityScalar.value) && //
          unit.equals(quantityScalar.unit) && //
          exponent.equals(quantityScalar.exponent);
    }
    return false;
  }

  @Override
  public String toString() {
    return value + "[" + unit + "^" + exponent + "]";
  }
}
