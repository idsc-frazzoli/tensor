// code by gjoel
// formula from https://en.wikipedia.org/wiki/Error_function
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sign;

/** the implementation guarantees an error smaller than 1.2 x 10^-7
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Erf.html">Erf</a> */
public enum Erf implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final ScalarUnaryOperator SERIES = Series.of(Tensors.vector( //
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
  ));
  private static final Scalar HALF = DoubleScalar.of(0.5);

  @Override
  public Scalar apply(Scalar scalar) {
    Scalar t = Abs.of(scalar).multiply(HALF).add(RealScalar.ONE).reciprocal();
    Scalar x2 = AbsSquared.FUNCTION.apply(scalar);
    Scalar tau = Exp.FUNCTION.apply(SERIES.apply(t).subtract(x2)).multiply(t);
    return Sign.isPositiveOrZero(scalar) //
        ? RealScalar.ONE.subtract(tau)
        : tau.subtract(RealScalar.ONE);
  }

  /** @param tensor
   * @return tensor with all scalar entries replaced by the evaluation under Erf */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
