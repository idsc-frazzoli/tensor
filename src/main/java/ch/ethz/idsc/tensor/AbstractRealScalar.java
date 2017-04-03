// code by jph
package ch.ethz.idsc.tensor;

/** abs() returns this or this.negate() depending on whichever is non-negative */
public abstract class AbstractRealScalar extends AbstractScalar implements RealScalar {
  @Override // from RealScalar
  public final RealScalar abs() {
    return isPositive() ? this : negate();
  }

  @Override // from RealScalar
  public final RealScalar absSquared() {
    return (RealScalar) multiply(this);
  }

  @Override // from RealScalar
  public final RealScalar conjugate() {
    return this;
  }

  @Override // from RealScalar
  public final int signInt() {
    return this instanceof ZeroScalar ? 0 : (isPositive() ? 1 : -1);
  }

  /** @return true if this scalar is strictly greater zero, false otherwise */
  protected abstract boolean isPositive();
}
