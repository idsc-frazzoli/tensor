// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

// EXPERIMENTAL
enum Unitize implements ScalarUnaryOperator {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return Scalars.isZero(scalar) ? RealScalar.ZERO : RealScalar.ONE;
  }
}
