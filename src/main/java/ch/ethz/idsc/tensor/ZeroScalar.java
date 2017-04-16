// code by jph
package ch.ethz.idsc.tensor;

/** ZeroScalar represents the exact number 0
 * 
 * <p>all fields implemented using {@link Scalar}
 * are required to encode 0 as ZeroScalar.get().
 * 
 * <p>For instance, there are no {@link DoubleScalar} instance
 * with double value 0.0, and no {@link RationalScalar} instance of 0/1. */
public final class ZeroScalar extends AbstractRealScalar {
  private static final ZeroScalar INSTANCE = new ZeroScalar();

  /** @return instance representing 0 */
  public static ZeroScalar get() {
    return INSTANCE;
  }

  private ZeroScalar() {
  }

  @Override // from Scalar
  public ZeroScalar negate() {
    return this;
  }

  @Override // from Scalar
  public Scalar invert() { // very forbidden operation
    throw new ArithmeticException();
  }

  @Override // from Scalar
  public ZeroScalar multiply(Scalar scalar) {
    return this;
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    return scalar;
  }

  @Override // from AbstractRealScalar
  protected boolean isNonNegative() {
    return true;
  }

  @Override // from RealScalar
  public Number number() {
    return Integer.valueOf(0);
  }

  @Override
  public Scalar n() {
    return this;
  }

  @Override // from RealScalar
  public int compareTo(Scalar scalar) {
    if (scalar instanceof ZeroScalar)
      return 0;
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) scalar;
    return -comparable.compareTo(this);
  }

  @Override // from AbstractScalar
  public int hashCode() {
    return 0;
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    // multiple instance are possible due to serialization
    return object instanceof ZeroScalar;
  }

  @Override // from AbstractScalar
  public String toString() {
    return "0";
  }
}
