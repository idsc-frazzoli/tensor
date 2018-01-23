// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

/** the color data function maps an input {@link Scalar} value from the
 * interval [0, 1] to a 4-vector {r, g, b, a} with rgba entries.
 * 
 * <p>Each color component in the output tensor is an integer or
 * double value in the semi-open interval [0, 256).
 * Because {@link ColorFormat} uses Number::intValue to obtain the
 * int color component, the value 256 is not allowed and results in an Exception.
 * 
 * <p>If the input scalar does not satisfy {@link NumberQ},
 * the result is {0, 0, 0, 0}, which corresponds to a transparent color.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ColorDataFunction.html">ColorDataFunction</a> */
public interface ColorDataFunction extends ScalarTensorFunction {
  /** @return modifiable numeric vector {0.0, 0.0, 0.0, 0.0} which represents transparent rgba color */
  static Tensor transparent() {
    return StaticHelper.transparent();
  }
}
