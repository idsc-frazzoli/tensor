// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;

/** @see CirclePoints
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/AngleVector.html">AngleVector</a> */
public enum AngleVector {
  ;
  /** @param angle in radians
   * @return vector as {Cos[angle], Sin[angle]} */
  public static Tensor of(Scalar angle) {
    return Tensors.of(Cos.FUNCTION.apply(angle), Sin.FUNCTION.apply(angle));
  }
}
