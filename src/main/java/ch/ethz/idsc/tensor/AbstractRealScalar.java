// code by jph
package ch.ethz.idsc.tensor;

public abstract class AbstractRealScalar extends AbstractScalar implements RealScalar {
  /** @return this or this.negate() depending on whichever is non-negative */
  @Override // from Scalar
  public final Scalar abs() {
    return isNonNegative() ? this : negate();
  }

  @Override // from ConjugateInterface
  public final Scalar conjugate() {
    return this;
  }

  @Override // from RealScalar
  public final int signInt() {
    return this instanceof ZeroScalar ? 0 : (isNonNegative() ? 1 : -1);
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
  // methods are non-final because other RealScalars may support better precision
  @Override // from SqrtInterface
  public Scalar sqrt() {
    if (isNonNegative())
      return DoubleScalar.of(Math.sqrt(number().doubleValue()));
    return ComplexScalar.of(ZeroScalar.get(), DoubleScalar.of(Math.sqrt(-number().doubleValue())));
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return isNonNegative() ? ZeroScalar.get() : DoubleScalar.of(Math.PI);
  }

  /***************************************************/
  /** @return true if this scalar is zero, or strictly greater zero, false otherwise */
  protected abstract boolean isNonNegative();
}
