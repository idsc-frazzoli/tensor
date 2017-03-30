// code by jph
package ch.ethz.idsc.tensor;

/** ZeroScalar represents the exact number 0
 * <p>
 * all fields implemented using {@link Scalar}
 * are required to encode 0 as ZeroScalar.get().
 * <p>
 * For instance, there are no {@link DoubleScalar} instance
 * with double value 0.0, and no {@link RationalScalar} instance of 0/1. */
public final class ZeroScalar extends RealScalar implements ExactPrecision {
  private static final ZeroScalar INSTANCE = new ZeroScalar();

  /** @return instance representing 0 */
  public static ZeroScalar get() {
    return INSTANCE;
  }

  private ZeroScalar() {
  }

  @Override // from Tensor
  public ZeroScalar negate() {
    return this;
  }

  @Override // from Scalar
  public Scalar invert() { // very forbidden operation
    throw new ArithmeticException();
  }

  @Override
  protected boolean isPositive() {
    return false;
  }

  @Override // from Scalar
  protected Scalar plus(Scalar scalar) {
    return scalar;
  }

  @Override // from Scalar
  public ZeroScalar multiply(Scalar scalar) {
    return this;
  }

  @Override
  public Number number() {
    return Integer.valueOf(0);
  }

  @Override // from Comparable<RealScalar>
  public int compareTo(RealScalar realScalar) {
    return -realScalar.getSignInt();
  }

  @Override // from Object
  public int hashCode() {
    return 0;
  }

  @Override // from Object
  public boolean equals(Object object) {
    // multiple instance are possible due to serialization
    return object instanceof ZeroScalar;
  }

  @Override // from Object
  public String toString() {
    return "0";
  }
}
