// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.MachineNumberQInterface;

/** scalar with double precision, 64-bit, MATLAB style
 * 
 * <p>The value of {@link DoubleScalar} is backed by a double type.
 * double types are <em>not</em> closed under multiplicative inversion:
 * <pre>
 * a / b != a * (1.0 / b)
 * </pre>
 * For instance, the smallest double is 4.9E-324.
 * But 1.0 / 4.9E-324 == Infinity.
 * 
 * <p>The range of double values closed under 2x inversion, i.e.
 * value == 1.0 / (1.0 / value) is
 * [5.562684646268010E-309, 1.7976931348623151E308]
 * 
 * <p>zero().reciprocal() equals {@link DoubleScalar#POSITIVE_INFINITY}
 * 
 * <p>DoubleScalar is comparable to Scalar types that implement {@link RealScalar}.
 * 
 * <p>The numeric zero has a sign, i.e. positive +0.0 and negative -0.0 exist.
 * The implementation of DoubleScalar uses the following rules:
 * <ul>
 * <li>DoubleScalar.of(-0.0) is backed by the double value -0.0
 * <li>DoubleScalar.of(-0.0) equals DoubleScalar.of(0.0)
 * <li>their hashCode is also identical, which is not the case when using
 * {@link Double#hashCode(double)}
 * <li>Scalars.compare(DoubleScalar.of(-0.0), DoubleScalar.of(0.0)) gives 0
 * </ul>
 * 
 * <p>Special case:
 * Scalars.fromString("-0.0") gives DoubleScalar.of(0.0) */
public final class DoubleScalar extends AbstractRealScalar implements //
    ChopInterface, MachineNumberQInterface, Serializable {
  /** real scalar that encodes +Infinity. value is backed by Double.POSITIVE_INFINITY */
  public static final Scalar POSITIVE_INFINITY = of(Double.POSITIVE_INFINITY);
  /** real scalar that encodes -Infinity. value is backed by Double.NEGATIVE_INFINITY */
  public static final Scalar NEGATIVE_INFINITY = of(Double.NEGATIVE_INFINITY);
  /** real scalar that encodes NaN. value is backed by Double.NaN == 0.0d / 0.0
   * field name inspired by Mathematica::Indeterminate */
  public static final Scalar INDETERMINATE = of(Double.NaN);
  // ---
  /** positive numeric zero */
  private static final Scalar DOUBLE_ZERO = of(0.0);

  /** @param value
   * @return new instance of {@link DoubleScalar} */
  public static RealScalar of(double value) {
    return new DoubleScalar(value);
  }

  private final double value;

  /** private constructor is only called from of(...)
   * 
   * @param value */
  private DoubleScalar(double value) {
    this.value = value;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar negate() {
    return of(-value);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(value * scalar.number().doubleValue());
    return scalar.multiply(this);
  }

  // implementation exists because 4.9E-324 / 4.9E-324 != 4.9E-324 * (1 / 4.9E-324)
  @Override // from AbstractScalar
  public Scalar divide(Scalar scalar) {
    // if (scalar instanceof RationalScalar)
    // return of(value * scalar.reciprocal().number().doubleValue());
    if (scalar instanceof RealScalar)
      return of(value / scalar.number().doubleValue());
    return scalar.under(this);
  }

  @Override // from AbstractScalar
  public Scalar under(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(scalar.number().doubleValue() / value);
    return scalar.divide(this);
  }

  /** DOUBLE_ZERO.reciprocal() == Double.POSITIVE_INFINITY */
  @Override // from Scalar
  public Scalar reciprocal() {
    return of(1.0 / value);
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar zero() {
    return DOUBLE_ZERO;
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(value + scalar.number().doubleValue());
    return scalar.add(this);
  }

  /***************************************************/
  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (Double.isNaN(value))
      throw TensorRuntimeException.of(this, scalar);
    if (scalar instanceof RealScalar) {
      double other = scalar.number().doubleValue();
      if (Double.isNaN(other))
        throw TensorRuntimeException.of(this, scalar);
      if (value == other) // +0.0 == -0.0
        return 0;
      return Double.compare(value, other);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return isMachineNumber() ? RationalScalar.of(StaticHelper.ceiling(bigDecimal()), BigInteger.ONE) : this;
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return Math.abs(value) < chop.threshold() ? ZERO : this;
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return isMachineNumber() ? RationalScalar.of(StaticHelper.floor(bigDecimal()), BigInteger.ONE) : this;
  }

  /** @return true if the argument is a finite floating-point
   * value; false otherwise (for NaN and infinity arguments). */
  @Override // from MachineNumberQInterface
  public boolean isMachineNumber() {
    return Double.isFinite(value);
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return isMachineNumber() //
        ? RationalScalar.of(bigDecimal().setScale(0, RoundingMode.HALF_UP).toBigIntegerExact(), BigInteger.ONE)
        : this;
  }

  @Override // from SignInterface
  public int signInt() {
    if (Double.isNaN(value))
      throw TensorRuntimeException.of(this);
    return value < 0 ? -1 : (0 == value ? 0 : 1);
  }

  // helper function used for implementation in RoundingInterface
  private BigDecimal bigDecimal() {
    return BigDecimal.valueOf(value);
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    // ensure that +0.0 and -0.0 return same hash value
    return Double.hashCode(value == 0.0 ? 0.0 : value);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    return object instanceof RealScalar && value == ((RealScalar) object).number().doubleValue();
  }

  @Override // from AbstractScalar
  public String toString() {
    return Double.toString(value);
  }
}
