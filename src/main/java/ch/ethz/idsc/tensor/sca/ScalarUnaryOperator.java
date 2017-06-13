// code by jph
package ch.ethz.idsc.tensor.sca;

import java.io.Serializable;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Scalar;

/** serializable interface for functions that map a {@link Scalar} to another {@link Scalar} */
public interface ScalarUnaryOperator extends UnaryOperator<Scalar>, Serializable {
  // ---
}
