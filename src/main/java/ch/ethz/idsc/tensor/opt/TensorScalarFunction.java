// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** function f that maps a {@link Tensor} to a {@link Scalar}
 * 
 * Examples:
 * 1) an implicit function that defines a region as {x | f(x) < 0}
 * 2) a smooth noise function that maps a vector to a value in the interval [-1, 1] */
public interface TensorScalarFunction extends Function<Tensor, Scalar>, Serializable {
  // ---
}
