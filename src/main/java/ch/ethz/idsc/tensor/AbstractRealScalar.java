// code by jph
package ch.ethz.idsc.tensor;

public abstract class AbstractRealScalar extends AbstractScalar implements RealScalar {
  /** @return this or this.negate() depending on whichever is non-negative */
  @Override // from Scalar
  public final Scalar abs() {
    return isPositive() ? this : negate();
  }

  @Override // from ConjugateInterface
  public final Scalar conjugate() {
    return this;
  }

  @Override // from RealScalar
  public final int signInt() {
    return this instanceof ZeroScalar ? 0 : (isPositive() ? 1 : -1);
  }

  @Override // from RealInterface
  public final Scalar real() {
    return this;
  }

  @Override // from ImagInterface
  public final Scalar imag() {
    return ZeroScalar.get();
  }

  /***************************************************/
  /** @return true if this scalar is strictly greater zero, false otherwise */
  protected abstract boolean isPositive();
}
