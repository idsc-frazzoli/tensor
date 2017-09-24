// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/AngleVector.html">AngleVector</a> */
public enum AngleVector {
  ;
  /** @param theta
   * @return vector as {Cos[theta], Sin[theta]} */
  public static Tensor of(Scalar theta) {
    return Tensors.of(Cos.FUNCTION.apply(theta), Sin.FUNCTION.apply(theta));
  }
}
