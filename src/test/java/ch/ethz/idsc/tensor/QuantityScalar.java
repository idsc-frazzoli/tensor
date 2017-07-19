// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

public class QuantityScalar extends AbstractScalar implements //
    ArcTanInterface, ChopInterface, ComplexEmbedding, ExactNumberQInterface, NInterface, //
    PowerInterface, RoundingInterface, SignInterface, SqrtInterface, Comparable<Scalar> {
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
    if (Scalars.isZero(this) && Scalars.nonZero(scalar))
      return scalar;
    if (Scalars.isZero(scalar) && Scalars.nonZero(this))
      return this;
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      if (Scalars.isZero(this) && Scalars.isZero(scalar)) {
        if (unitMap.equals(quantityScalar.unitMap))
          return this; // TODO make a test for this later
        return RealScalar.ZERO;
      }
      if (unitMap.equals(quantityScalar.unitMap))
        return of(value.add(quantityScalar.value), unitMap);
    } else {
      // scalar is not QuantityScalar
      if (Scalars.isZero(this) && Scalars.isZero(scalar))
        return this;
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

  @Override // from ExactNumberQInterface
  public boolean isExactNumber() {
    return ExactNumberQ.of(value);
  }

  @Override
  public Scalar power(Scalar exponent) {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar arcTan(Scalar x) {
    if (x instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) x;
      if (unitMap.equals(quantityScalar.unitMap))
        return ArcTan.of(quantityScalar.value, value);
    }
    throw TensorRuntimeException.of(x, this);
  }

  @Override
  public Scalar sqrt() {
    return of(Sqrt.of(value), unitMap.multiply(RationalScalar.of(1, 2)));
  }

  @Override
  public Scalar ceiling() {
    return of(Ceiling.of(value), unitMap);
  }

  @Override
  public Scalar chop(Chop chop) {
    return of(chop.apply(value), unitMap);
  }

  @Override
  public Scalar conjugate() {
    return of(Conjugate.of(value), unitMap);
  }

  @Override
  public Scalar floor() {
    return of(Floor.of(value), unitMap);
  }

  @Override
  public Scalar imag() {
    return of(Imag.of(value), unitMap);
  }

  @Override
  public Scalar n() {
    return of(N.of(value), unitMap);
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
  public Scalar round() {
    return of(Round.of(value), unitMap);
  }

  @Override
  public int compareTo(Scalar scalar) {
    if (scalar instanceof QuantityScalar) {
      QuantityScalar quantityScalar = (QuantityScalar) scalar;
      if (unitMap.equals(quantityScalar.unitMap))
        return Scalars.compare(value, quantityScalar.value);
    }
    // TODO too strict for pivot
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unitMap);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof QuantityScalar) {
      // System.out.println("HERE " + this + " " + object);
      QuantityScalar quantityScalar = (QuantityScalar) object;
      return value.equals(quantityScalar.value) && //
          unitMap.equals(quantityScalar.unitMap);
    }
    if (object instanceof Scalar) {
      Scalar scalar = (Scalar) object;
      return Scalars.isZero(this) && Scalars.isZero(scalar);
    }
    return false;
  }

  @Override
  public String toString() {
    return value + "[" + unitMap + "]";
  }
}
