// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** maps a value in the interval [0, 1] to a 4-vector with rgba entries
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ColorDataFunction.html">ColorDataFunction</a> */
// EXPERIMENTAL
public interface ColorDataFunction extends Function<Scalar, Tensor> {
  // ---
}
