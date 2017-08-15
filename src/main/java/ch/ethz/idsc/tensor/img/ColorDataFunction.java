// code by jph
package ch.ethz.idsc.tensor.img;

import java.io.Serializable;
import java.util.function.Function;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** the color data function maps a {@link Scalar} value
 * in the interval [0, 1] to a 4-vector {r, g, b, a} with rgba entries.
 * each color component is a value in the semi-open interval [0, 256).
 * The value 256 is not allowed and results in an Exception.
 * 
 * <p>If the input scalar does not satisfy {@link NumberQ},
 * the result is {0, 0, 0, 0}, which corresponds to a transparent color.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ColorDataFunction.html">ColorDataFunction</a> */
public interface ColorDataFunction extends Function<Scalar, Tensor>, Serializable {
  // ---
}
