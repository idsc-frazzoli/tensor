// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.MachineNumberQInterface;

/** scalar with double precision, 64-bit, MATLAB style
 * 
 * zero().inverse() equals {@link RealScalar#POSITIVE_INFINITY} */
public final class DoubleScalar extends AbstractRealScalar implements //
    ChopInterface, MachineNumberQInterface {
  private static final Scalar DOUBLE_ZERO = of(0.);

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
  public Scalar invert() {
    return of(1 / value);
  }

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
  @Override // from AbstractRealScalar
  protected boolean isNonNegative() {
    return 0 <= value;
  }

  /***************************************************/
  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return Double.compare(number().doubleValue(), scalar.number().doubleValue());
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) scalar;
    return -comparable.compareTo(this);
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return RationalScalar.of(StaticHelper.ceiling(bigDecimal()), BigInteger.ONE);
  }

  @Override // from ChopInterface
  public Scalar chop(double threshold) {
    return Math.abs(value) < threshold ? ZERO : this;
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return RationalScalar.of(StaticHelper.floor(bigDecimal()), BigInteger.ONE);
  }

  @Override // from MachineNumberQInterface
  public boolean isMachineNumber() {
    return isFinite();
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return RationalScalar.of(bigDecimal().setScale(0, RoundingMode.HALF_UP).toBigIntegerExact(), BigInteger.ONE);
  }

  /***************************************************/
  private BigDecimal bigDecimal() {
    return BigDecimal.valueOf(value);
  }

  /** @return true if the argument is a finite floating-point
   * value; false otherwise (for NaN and infinity arguments). */
  public boolean isFinite() {
    return Double.isFinite(value);
  }

  public boolean isInfinite() {
    return Double.isInfinite(value);
  }

  public boolean isNaN() {
    return Double.isNaN(value);
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Double.hashCode(value);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) object;
      return value == realScalar.number().doubleValue();
    }
    return object == null ? false : object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    return Double.toString(value);
  }
}
