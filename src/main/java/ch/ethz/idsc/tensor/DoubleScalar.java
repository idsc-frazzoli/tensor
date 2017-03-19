// code by jph
package ch.ethz.idsc.tensor;

/** scalar with double precision, 64-bit, MATLAB style */
public final class DoubleScalar extends RealScalar {
  /** @param value
   * @return new instance of {@link DoubleScalar}, or {@link ZeroScalar} if value == 0 */
  public static RealScalar of(double value) {
    return value == 0 ? ZeroScalar.get() : new DoubleScalar(value);
  }

  private final double value;

  /** private constructor is only called from of(...)
   * 
   * @param value */
  private DoubleScalar(double value) {
    this.value = value;
  }

  @Override // from Scalar
  public RealScalar invert() {
    return of(1 / value);
  }

  @Override // from Scalar
  protected boolean isPositive() {
    return 0 < value;
  }

  @Override // from Tensor
  public RealScalar negate() {
    return of(-value);
  }

  @Override // from Scalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(value + scalar.number().doubleValue());
    return scalar.plus(this);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(value * scalar.number().doubleValue());
    return scalar.multiply(this);
  }

  @Override
  public Number number() {
    return value;
  }

  @Override // from Comparable<RealScalar>
  public int compareTo(RealScalar realScalar) {
    return Double.compare(number().doubleValue(), realScalar.number().doubleValue());
  }

  @Override // from Object
  public int hashCode() {
    return Double.hashCode(value);
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) object;
      return value == realScalar.number().doubleValue();
    }
    return object == null ? false : object.equals(this);
  }

  @Override // from Object
  public String toString() {
    return "" + value;
  }
}
