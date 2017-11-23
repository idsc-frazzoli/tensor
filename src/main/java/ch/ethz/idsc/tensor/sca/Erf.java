// code by gjoel
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.*;
import ch.ethz.idsc.tensor.alg.Multinomial;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Erf.html">Erfc</a> */
public enum Erf implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Tensor coeffs = Tensors.vector( //
      -1.26551223, //
      +1.00002368, // x
      +0.37409196, // x^2
      +0.09678418, // x^3
      -0.18628806, // x^4
      +0.27886807, // x^5
      -1.13520398, // x^6
      +1.48851587, // x^7
      -0.82215223, // x^8
      +0.17087277 // x^9
  );

  @Override
  // error < 1.2 x 10^-7
  public Scalar apply(Scalar scalar) {
    Scalar t = RealScalar.ONE.add( Abs.of(scalar).divide(RealScalar.of(2.0)) ).reciprocal();
    Scalar x2 = AbsSquared.FUNCTION.apply(scalar);
    Scalar tau = t.multiply(Exp.FUNCTION.apply( x2.negate().add(Multinomial.horner(coeffs, t)) ));
    if (Sign.isPositiveOrZero(scalar))
      return RealScalar.ONE.subtract(tau);
    else
      return tau.subtract(RealScalar.ONE);
  }
}
