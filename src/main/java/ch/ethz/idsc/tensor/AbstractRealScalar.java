// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.RealInterface;

/** suggested base class for implementations of {@link RealScalar} */
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
    return isNonNegative() ? (Scalars.isZero(this) ? 0 : 1) : -1;
  }

  @Override // from RealInterface
  public final Scalar real() {
    return this;
  }

  @Override // from ImagInterface
  public final Scalar imag() {
    return ZERO;
  }

  /***************************************************/
  // methods are non-final because other RealScalars may support better precision
  /** @return {@link ComplexScalar} if negative */
  @Override // from SqrtInterface
  public Scalar sqrt() {
    if (isNonNegative())
      return DoubleScalar.of(Math.sqrt(number().doubleValue()));
    return ComplexScalar.of(zero(), DoubleScalar.of(Math.sqrt(-number().doubleValue())));
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return isNonNegative() ? RealScalar.ZERO : DoubleScalar.of(Math.PI);
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (Scalars.isZero(this)) {
      if (Scalars.isZero(exponent))
        return RealScalar.ONE; // <- not generic
      if (exponent instanceof RealInterface) {
        RealInterface realInterface = (RealInterface) exponent;
        RealScalar realScalar = (RealScalar) realInterface.real();
        if (realScalar.signInt() == 1)
          return zero();
      }
      throw TensorRuntimeException.of(this, exponent);
    }
    if (exponent instanceof RealScalar)
      return RealScalar.of(Math.pow(number().doubleValue(), exponent.number().doubleValue()));
    return Exp.function.apply(exponent.multiply(Log.function.apply(this)));
  }

  /***************************************************/
  /** @return true if this scalar is zero, or strictly greater zero, false otherwise */
  protected abstract boolean isNonNegative();
}
