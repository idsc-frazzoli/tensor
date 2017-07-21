// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** the class is intended for testing and demonstration
 * 
 * the implementation is consistent with Mathematica:
 * NumberQ[Quantity[3, "Meters"]] == False
 * ExactNumberQ[Quantity[3, "Meters"]] == False
 * MachineNumberQ[Quantity[3.123, "Meters"]] == False
 * 
 * the convention of equality: "0[units] == 0 evaluates to true"
 * is used in
 * {@link #plus(Scalar)}
 * {@link #compareTo(Scalar)}
 * {@link #equals(Object)}
 * 
 * In particular, the rule allows to up-cast any
 * {@link Scalar#zero()} to a zero with any unit,
 * for instance 0 == 0[m^2]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantity.html">Quantity</a> */
public class Quantity extends AbstractScalar implements //
    ArcTanInterface, ChopInterface, ComplexEmbedding, NInterface, //
    PowerInterface, RoundingInterface, SignInterface, SqrtInterface, Comparable<Scalar> {
  public static Scalar fromString(String string) {
    int index = string.indexOf(UnitMap.OPENING_BRACKET);
    if (0 < index) {
      Scalar value = Scalars.fromString(string.substring(0, index));
      UnitMap unitMap = UnitMap.of(string.substring(index));
      return of(value, unitMap);
    }
    return Scalars.fromString(string);
  }

  /** @param value
   * @param string, for instance "[m*s^-2]"
   * @return */
  public static Scalar of(Scalar value, String string) {
    if (value instanceof Quantity)
      throw TensorRuntimeException.of(value);
    return of(value, UnitMap.of(string));
  }

  /** @param number
   * @param string, for instance "[kg^3*m*s^-2]"
   * @return */
  public static Scalar of(Number number, String string) {
    return of(RealScalar.of(number), UnitMap.of(string));
  }

  /***************************************************/
  private static final Scalar HALF = RationalScalar.of(1, 2);

  private static Scalar of(Scalar value, UnitMap unitMap) {
    return unitMap.isEmpty() ? value : new Quantity(value, unitMap);
  }

  private final Scalar value;
  private final UnitMap unitMap;

  private Quantity(Scalar value, UnitMap unitMap) {
    if (unitMap.isEmpty())
      throw TensorRuntimeException.of(value);
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
      return scalar; // 0[m] + X(X!=0) gives X(X!=0)
    if (Scalars.nonZero(this) && Scalars.isZero(scalar))
      return this; // X(X!=0) + 0[m] gives X(X!=0)
    if (scalar instanceof Quantity) {
      Quantity quantityScalar = (Quantity) scalar;
      if (Scalars.isZero(this) && Scalars.isZero(scalar)) {
        // explicit addition of zeros to ensure symmetry
        // for instance when numeric precision is different:
        // 0[m] + 0.0[m] == 0.0[m]
        // 0[m] + 0.0[s] == 0.0
        final Scalar zero = value.add(quantityScalar.value);
        if (unitMap.equals(quantityScalar.unitMap))
          return of(zero, unitMap); // 0[m] + 0[m] gives 0[m]
        return zero; // 0[m] + 0[s] gives 0
      }
      if (unitMap.equals(quantityScalar.unitMap))
        return of(value.add(quantityScalar.value), unitMap);
    } else { // <- scalar is not an instance of QuantityScalar
      if (Scalars.isZero(this) && Scalars.isZero(scalar))
        // return of value.add(scalar) is not required for symmetry
        // precision of this.value prevails over given scalar
        return this; // 0[kg] + 0 gives 0[kg]
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantityScalar = (Quantity) scalar;
      return of(value.multiply(quantityScalar.value), unitMap.add(quantityScalar.unitMap));
    }
    return of(value.multiply(scalar), unitMap);
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    // Mathematica allows 2[m]^3[s], but the tensor library does not:
    if (exponent instanceof Quantity)
      throw TensorRuntimeException.of(this, exponent);
    return of(Power.of(value, exponent), unitMap.multiply(exponent));
  }

  @Override // from ArcTanInterface
  public Scalar arcTan(Scalar x) {
    if (x instanceof Quantity) {
      Quantity quantityScalar = (Quantity) x;
      if (unitMap.equals(quantityScalar.unitMap))
        return ArcTan.of(quantityScalar.value, value);
    }
    throw TensorRuntimeException.of(x, this);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return of(Sqrt.of(value), unitMap.multiply(HALF));
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return of(Ceiling.of(value), unitMap);
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return of(chop.apply(value), unitMap);
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return of(Conjugate.of(value), unitMap);
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return of(Floor.of(value), unitMap);
  }

  @Override // from ComplexEmbedding
  public Scalar imag() {
    return of(Imag.of(value), unitMap);
  }

  @Override // from NInterface
  public Scalar n() {
    return of(N.of(value), unitMap);
  }

  @Override // from SignInterface
  public int signInt() {
    SignInterface signInterface = (SignInterface) value;
    return signInterface.signInt();
  }

  @Override // from ComplexEmbedding
  public Scalar real() {
    return of(Real.of(value), unitMap);
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return of(Round.of(value), unitMap);
  }

  @Override
  public int compareTo(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantityScalar = (Quantity) scalar;
      if (unitMap.equals(quantityScalar.unitMap))
        return Scalars.compare(value, quantityScalar.value);
    } else { // <- scalar is not an instance of QuantityScalar
      if (Scalars.isZero(scalar))
        // treats the cases: -3[m] < 0, and 0 < +2[s]
        return Scalars.compare(value, scalar);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unitMap);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Quantity) {
      Quantity quantityScalar = (Quantity) object;
      return value.equals(quantityScalar.value) && //
          unitMap.equals(quantityScalar.unitMap);
    } // else
    if (object instanceof Scalar) {
      // the implementation of plus(...) uses the convention
      // that 0[kg] == 0 evaluates to true
      Scalar scalar = (Scalar) object;
      return Scalars.isZero(this) && Scalars.isZero(scalar); // 0[kg] == 0
    }
    return false;
  }

  @Override
  public String toString() {
    return value.toString() + unitMap;
  }
}
