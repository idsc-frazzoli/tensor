// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** n * Quotient[m, n, d] + Mod[m, n, d] is always equal to m.
 *
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quotient.html">Quotient</a> */
/* package */ class Quotient implements ScalarUnaryOperator {
  // LONGTERM
  @Override
  public Scalar apply(Scalar scalar) {
    throw TensorRuntimeException.of(scalar);
  }
}
