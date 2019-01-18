// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.BinaryPower;

/** instantiated by {@link Scalars#binaryPower(Scalar)} */
/* package */ class ScalarBinaryPower extends BinaryPower<Scalar> {
  public static final ScalarBinaryPower REAL_SCALAR = new ScalarBinaryPower(RealScalar.ONE);
  // ---
  private final Scalar one;

  /** @param one assumed to be non-null */
  public ScalarBinaryPower(Scalar one) {
    this.one = one;
  }

  @Override // from BinaryPower
  public Scalar zeroth() {
    return one;
  }

  @Override // from BinaryPower
  public Scalar invert(Scalar scalar) {
    return scalar.reciprocal();
  }

  @Override // from BinaryPower
  public Scalar multiply(Scalar s1, Scalar s2) {
    return s1.multiply(s2);
  }
}
