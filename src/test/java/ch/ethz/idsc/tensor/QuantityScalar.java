// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
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

public class QuantityScalar extends AbstractScalar implements //
    ArcTanInterface, ChopInterface, ConjugateInterface, NInterface, PowerInterface, //
    RealInterface, SignInterface, SqrtInterface, Comparable<Scalar> {
  /** @param value
   * @param unit
   * @param exponent
   * @return */
  public static Scalar of(Scalar value, String unit, Scalar exponent) {
    return of(value, new UnitMap(unit, exponent));
  }

  public static Scalar of(Scalar value, UnitMap unitMap) {
    return unitMap.isEmpty() ? value : new QuantityScalar(value, unitMap);
  }

  private final Scalar value;
  private final UnitMap unitMap;

  private QuantityScalar(Scalar value, UnitMap unitMap) {
    this.value = value;
    this.unitMap = unitMap;
  }

  @Override
  public Scalar negate() {
    return of(value.negate(), unitMap);
  }

  @Override
  public Scalar invert() {
    return of(value.invert(), unitMap.negate());
  }

  @Override
  public Scalar abs() {
    return of(value.abs(), unitMap);
  }

  @Override
  public Number number() {
    return value.number();
  }

  @Override
  public Scalar zero() {
    return of(value.zero(), unitMap);
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (Scalars.isZero(this))
      return scalar;
    if (Scalars.isZero(scalar))
      return this;
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      if (unitMap.equals(quantityScalar.unitMap))
        return of(value.add(quantityScalar.value), unitMap);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      return of(value.multiply(quantityScalar.value), unitMap.add(quantityScalar.unitMap));
    }
    return of(value.multiply(scalar), unitMap);
  }

  @Override
  public Scalar power(Scalar exponent) {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar arcTan(Scalar y) {
    if (y instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) y;
      if (unitMap.equals(quantityScalar.unitMap))
        return ArcTan.of(value, quantityScalar.value);
    }
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar sqrt() {
    return of(Sqrt.of(value), unitMap.multiply(RationalScalar.of(1, 2)));
  }

  @Override
  public Scalar chop(double threshold) {
    return of((Scalar) Chop.of(value), unitMap);
  }

  @Override
  public Scalar conjugate() {
    return of(Conjugate.of(value), unitMap);
  }

  @Override
  public Scalar n() {
    return of((Scalar) N.of(value), unitMap);
  }

  @Override // from SignInterface
  public int signInt() {
    RealScalar realScalar = (RealScalar) value;
    return realScalar.signInt();
  }

  @Override
  public Scalar real() {
    return of(Real.of(value), unitMap);
  }

  @Override
  public int compareTo(Scalar object) {
    if (object instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) object;
      if (unitMap.equals(quantityScalar.unitMap))
        return Scalars.compare(value, quantityScalar.value);
      throw TensorRuntimeException.of(this, quantityScalar);
    }
    throw new IllegalArgumentException();
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unitMap);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) object;
      return value.equals(quantityScalar.value) && //
          unitMap.equals(quantityScalar.unitMap);
    }
    return false;
  }

  @Override
  public String toString() {
    return value + "[" + unitMap + "]";
  }
}
