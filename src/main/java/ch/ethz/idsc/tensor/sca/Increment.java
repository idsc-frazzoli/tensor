// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

public enum Increment implements Function<Scalar, Scalar> {
  ONE;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return scalar.add(RealScalar.ONE);
  }
}
