// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.BinaryPower;

/** instantiated by {@link Scalars#binaryPower(Scalar)} */
/* package */ class ScalarBinaryPower<T extends Scalar> extends BinaryPower<T> {
  public static final ScalarBinaryPower<Scalar> REAL = new ScalarBinaryPower<>(RealScalar.ONE);
  // ---
  private final T one;

  /** @param one assumed to be non-null */
  public ScalarBinaryPower(T one) {
    this.one = one;
  }

  @Override // from BinaryPower
  protected T zeroth() {
    return one;
  }

  @Override // from BinaryPower
  @SuppressWarnings("unchecked")
  protected T invert(T scalar) {
    return (T) scalar.reciprocal();
  }

  @Override // from BinaryPower
  @SuppressWarnings("unchecked")
  protected T multiply(T s1, T s2) {
    return (T) s1.multiply(s2);
  }
}
