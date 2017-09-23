// code by jph
package ch.ethz.idsc.tensor.qty;

import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class QuantityImpl extends AbstractScalar implements Quantity {
  private static final Scalar HALF = RationalScalar.of(1, 2);

  /* package */ static Scalar of(Scalar value, Unit unit) {
    return unit.isEmpty() ? value : new QuantityImpl(value, unit);
  }

  /***************************************************/
  private final Scalar value;
  private final Unit unit; // non-empty

  private QuantityImpl(Scalar value, Unit unit) {
    this.value = value;
    this.unit = unit;
  }

  @Override
  public Scalar value() {
    return value;
  }

  @Override
  public Unit unit() {
    return unit;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar negate() {
    return of(value.negate(), unit);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return of(value.multiply(quantity.value()), unit.add(quantity.unit()));
    }
    return of(value.multiply(scalar), unit);
  }

  @Override // from Scalar
  public Scalar divide(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return of(value.divide(quantity.value()), unit.add(quantity.unit().negate()));
    }
    return of(value.divide(scalar), unit);
  }

  @Override // from Scalar
  public Scalar under(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return of(value.under(quantity.value()), unit.negate().add(quantity.unit()));
    }
    return of(value.under(scalar), unit.negate());
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return of(value.reciprocal(), unit.negate());
  }

  @Override // from Scalar
  public Scalar abs() {
    return of(value.abs(), unit);
  }

  @Override // from Scalar
  public Number number() {
    return value.number();
  }

  @Override // from Scalar
  public Scalar zero() {
    return of(value.zero(), unit);
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (Scalars.isZero(value) && Scalars.nonZero(scalar))
      return scalar; // 0[m] + X(X!=0) gives X(X!=0)
    if (Scalars.nonZero(value) && Scalars.isZero(scalar))
      return this; // X(X!=0) + 0[m] gives X(X!=0)
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      if (Scalars.isZero(value) && Scalars.isZero(scalar)) {
        // explicit addition of zeros to ensure symmetry
        // for instance when numeric precision is different:
        // 0[m] + 0.0[m] == 0.0[m]
        // 0[m] + 0.0[s] == 0.0
        final Scalar zero = value.add(quantity.value());
        if (unit.equals(quantity.unit()))
          return of(zero, unit); // 0[m] + 0[m] gives 0[m]
        return zero; // 0[m] + 0[s] gives 0
      }
      if (unit.equals(quantity.unit()))
        return of(value.add(quantity.value()), unit);
    } else { // <- scalar is not an instance of Quantity
      if (Scalars.isZero(value) && Scalars.isZero(scalar))
        // return of value.add(scalar) is not required for symmetry
        // precision of this.value prevails over given scalar
        return this; // 0[kg] + 0 gives 0[kg]
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  /***************************************************/
  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    // Mathematica allows 2[m]^3[s], but the tensor library does not:
    if (exponent instanceof Quantity)
      throw TensorRuntimeException.of(this, exponent);
    return of(Power.of(value, exponent), unit.multiply(exponent));
  }

  @Override // from ArcTanInterface
  public Scalar arcTan(Scalar x) {
    if (Scalars.isZero(x))
      x = zero(); // in case x == 0[?], attach same units as this to x
    if (x instanceof Quantity) {
      Quantity quantity = (Quantity) x;
      if (unit.equals(quantity.unit()) || Scalars.isZero(value))
        return ArcTan.of(quantity.value(), value);
    }
    throw TensorRuntimeException.of(x, this);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return of(Sqrt.FUNCTION.apply(value), unit.multiply(HALF));
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return of(Ceiling.FUNCTION.apply(value), unit);
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return of(chop.apply(value), unit);
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return of(Conjugate.FUNCTION.apply(value), unit);
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return of(Floor.FUNCTION.apply(value), unit);
  }

  @Override // from ComplexEmbedding
  public Scalar imag() {
    return of(Imag.FUNCTION.apply(value), unit);
  }

  @Override // from NInterface
  public Scalar n() {
    return of(N.DOUBLE.apply(value), unit);
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    N n = N.in(mathContext);
    return of(n.apply(value), unit);
  }

  @Override // from SignInterface
  public int signInt() {
    SignInterface signInterface = (SignInterface) value;
    return signInterface.signInt();
  }

  @Override // from ComplexEmbedding
  public Scalar real() {
    return of(Real.FUNCTION.apply(value), unit);
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return of(Round.FUNCTION.apply(value), unit);
  }

  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (Scalars.isZero(value) || Scalars.isZero(scalar))
      // treats the cases:
      // 0[s^-1] < 1, and -2 < 0[kg]
      // -3[m] < 0, and 0 < +2[s]
      // -2[kg] < 0[V]
      return Scalars.compare(value, scalar);
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      if (unit.equals(quantity.unit()))
        return Scalars.compare(value, quantity.value());
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(value, unit);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof Quantity) {
      Quantity quantity = (Quantity) object;
      if (Scalars.isZero(value) && Scalars.isZero(quantity.value())) // 0[s] == 0[m]
        return true;
      return value.equals(quantity.value()) && unit.equals(quantity.unit()); // 2[kg] == 2[kg]
    }
    if (object instanceof Scalar) {
      Scalar scalar = (Scalar) object;
      return Scalars.isZero(value) && Scalars.isZero(scalar); // 0[V] == 0
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(value);
    stringBuilder.append(UNIT_OPENING_BRACKET);
    stringBuilder.append(unit);
    stringBuilder.append(UNIT_CLOSING_BRACKET);
    return stringBuilder.toString();
  }
}
