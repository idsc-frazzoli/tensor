// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Increment.html">Increment</a> */
public enum Increment implements ScalarUnaryOperator {
  /** increments a given {@link Scalar} by RealScalar.ONE */
  ONE;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return scalar.add(RealScalar.ONE);
  }
}
