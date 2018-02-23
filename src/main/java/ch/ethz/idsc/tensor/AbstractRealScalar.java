// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sign;

/** suggested base class for implementations of {@link RealScalar} */
public abstract class AbstractRealScalar extends AbstractScalar implements RealScalar {
  private static final Scalar PI = DoubleScalar.of(Math.PI);
  static final double LOG_LO = 0.75;
  static final double LOG_HI = 1.3;

  /** @return this or this.negate() depending on whichever is non-negative */
  @Override // from Scalar
  public final Scalar abs() {
    return isNonNegative() ? this : negate();
  }

  /***************************************************/
  @Override // from ComplexEmbedding
  public final Scalar conjugate() {
    return this;
  }

  @Override // from ComplexEmbedding
  public final Scalar imag() {
    return ZERO; // consistent with Mathematica::Im[3.] == 0
  }

  @Override // from ComplexEmbedding
  public final Scalar real() {
    return this;
  }

  /***************************************************/
  // methods are non-final because overriding classes may support better precision
  @Override // from ArcTanInterface
  public Scalar arcTan(Scalar x) {
    if (x instanceof ComplexScalar)
      return StaticHelper.arcTan(x, this);
    if (x instanceof RealScalar)
      return DoubleScalar.of(Math.atan2( //
          number().doubleValue(), // y
          x.number().doubleValue())); // x
    throw TensorRuntimeException.of(x, this);
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return isNonNegative() ? ZERO : PI;
  }

  @Override // from ExpInterface
  public Scalar exp() {
    return DoubleScalar.of(Math.exp(number().doubleValue()));
  }

  @Override // from LogInterface
  public Scalar log() {
    if (isNonNegative()) {
      double value = number().doubleValue();
      if (LOG_LO < value && value < LOG_HI)
        return DoubleScalar.of(Math.log1p(subtract(RealScalar.ONE).number().doubleValue()));
      return DoubleScalar.of(Math.log(value));
    }
    return ComplexScalarImpl.of(Log.FUNCTION.apply(negate()), PI);
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (Scalars.isZero(this)) {
      if (Scalars.isZero(exponent))
        return ONE;
      if (exponent instanceof ComplexEmbedding) {
        ComplexEmbedding complexEmbedding = (ComplexEmbedding) exponent;
        if (Sign.isPositive(complexEmbedding.real()))
          return zero();
      }
      throw TensorRuntimeException.of(this, exponent);
    }
    if (exponent instanceof RealScalar) {
      double result = Math.pow(number().doubleValue(), exponent.number().doubleValue());
      if (result == result) // !Double::isNaN
        return DoubleScalar.of(result);
    }
    return Exp.FUNCTION.apply(exponent.multiply(Log.FUNCTION.apply(this)));
  }

  /** implementation is used by {@link DoubleScalar},
   * and is a fallback option for {@link RationalScalar}
   * 
   * @return {@link ComplexScalar} if negative */
  @Override // from SqrtInterface
  public Scalar sqrt() {
    if (isNonNegative())
      return DoubleScalar.of(Math.sqrt(number().doubleValue()));
    return ComplexScalarImpl.of(zero(), DoubleScalar.of(Math.sqrt(-number().doubleValue())));
  }

  @Override // from TrigonometryInterface
  public Scalar cos() {
    return DoubleScalar.of(Math.cos(number().doubleValue()));
  }

  @Override // from TrigonometryInterface
  public Scalar cosh() {
    return DoubleScalar.of(Math.cosh(number().doubleValue()));
  }

  @Override // from TrigonometryInterface
  public Scalar sin() {
    return DoubleScalar.of(Math.sin(number().doubleValue()));
  }

  @Override // from TrigonometryInterface
  public Scalar sinh() {
    return DoubleScalar.of(Math.sinh(number().doubleValue()));
  }

  /***************************************************/
  /** @return true if this scalar is zero, or strictly greater zero, false otherwise */
  protected final boolean isNonNegative() {
    return 0 <= signInt();
  }
}
