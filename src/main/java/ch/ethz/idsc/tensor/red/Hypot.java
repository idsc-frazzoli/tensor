// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;

public enum Hypot implements BiFunction<Scalar, Scalar, Scalar> {
  bifunction;
  // ---
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    return DoubleScalar.of(Math.hypot(a.number().doubleValue(), b.number().doubleValue()));
  }
}
