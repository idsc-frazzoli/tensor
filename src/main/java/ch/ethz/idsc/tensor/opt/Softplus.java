// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Ramp;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** softplus is a smooth {@link Ramp}
 * 
 * <a href="https://en.wikipedia.org/wiki/Rectifier_(neural_networks)">documentation</a> */
public enum Softplus implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar LO = DoubleScalar.of(-50);
  private static final Scalar HI = DoubleScalar.of(+50);

  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.lessThan(HI, scalar))
      return scalar;
    if (Scalars.lessThan(scalar, LO))
      return scalar.zero();
    return Log.FUNCTION.apply(Increment.ONE.apply(Exp.FUNCTION.apply(scalar)));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their softplus */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
