// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Increment.html">Increment</a> */
public enum Increment implements Function<Scalar, Scalar> {
  /** increments a given {@link Scalar} by RealScalar.ONE */
  ONE;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return scalar.add(RealScalar.ONE);
  }
}
