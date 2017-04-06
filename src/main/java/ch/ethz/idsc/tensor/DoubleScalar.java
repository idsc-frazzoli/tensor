// code by jph
package ch.ethz.idsc.tensor;

/** scalar with double precision, 64-bit, MATLAB style */
public final class DoubleScalar extends AbstractRealScalar {
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
  public Scalar invert() {
    return of(1 / value);
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

  @Override // from RealScalar
  public RealScalar negate() {
    return of(-value);
  }

  @Override // from RealScalar
  public int compareTo(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return Double.compare(number().doubleValue(), scalar.number().doubleValue());
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) scalar;
    return -comparable.compareTo(this);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return of(value + scalar.number().doubleValue());
    return scalar.add(this);
  }

  @Override // from AbstractRealScalar
  protected boolean isPositive() {
    return 0 < value;
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
