// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.RealInterface;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

public class QuantityScalar extends AbstractScalar //
    implements ChopInterface, ConjugateInterface, NInterface, PowerInterface, //
    RealInterface, SignInterface, SqrtInterface, Comparable<Scalar> {
  /** @param value
   * @param unit
   * @param exponent
   * @return */
  public static Scalar of(Scalar value, String unit, Scalar exponent) {
    return Scalars.isZero(exponent) ? value : new QuantityScalar(value, unit, exponent);
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
    return of(value.invert(), unit, exponent.negate());
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
    if (Scalars.isZero(this))
      return scalar;
    if (Scalars.isZero(scalar))
      return this;
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
        return of(value.multiply(quantityScalar.value), unit, exponent.add(quantityScalar.exponent));
      throw TensorRuntimeException.of(this, scalar);
    }
    return of(value.multiply(scalar), unit, exponent);
  }

  @Override
  public Scalar power(Scalar exponent) {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar sqrt() {
    return of(Sqrt.of(value), unit, exponent.multiply(RationalScalar.of(1, 2)));
  }

  @Override
  public Scalar chop(double threshold) {
    return of((Scalar) Chop.of(value), unit, exponent);
  }

  @Override
  public Scalar conjugate() {
    return of(Conjugate.of(value), unit, exponent);
  }

  @Override
  public Scalar n() {
    return of((Scalar) N.of(value), unit, exponent);
  }

  @Override // from SignInterface
  public int signInt() {
    RealScalar realScalar = (RealScalar) value;
    return realScalar.signInt();
  }

  @Override
  public Scalar real() {
    return of(Real.of(value), unit, exponent);
  }

  @Override
  public int compareTo(Scalar object) {
    if (object instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) object;
      if (unit.equals(quantityScalar.unit) && //
          exponent.equals(quantityScalar.exponent))
        return Scalars.compare(value, quantityScalar.value);
    }
    throw TensorRuntimeException.of(this);
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
