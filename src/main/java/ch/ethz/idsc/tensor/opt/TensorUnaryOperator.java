// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Tensor;

/** serializable interface for functions that map a {@link Tensor} to another {@link Tensor} */
public interface TensorUnaryOperator extends UnaryOperator<Tensor>, Serializable {
  // ---
}
