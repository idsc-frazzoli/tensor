// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;

/** Rescale so that all the list elements run from 0 to 1
 * 
 * <code>
 * Rescale[{-.7, .5, 1.2, 5.6, 1.8}] == {0., 0.190476, 0.301587, 1., 0.396825}
 * </code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Rescale.html">Rescale</a> */
public enum Rescale {
  ;
  // ---
  /** @param vector
   * @return */
  public static Tensor of(Tensor vector) {
    if (vector.length() == 0)
      return Tensors.empty();
    Scalar min = vector.flatten(0).map(Scalar.class::cast).reduce(Min::of).get();
    Scalar max = vector.flatten(0).map(Scalar.class::cast).reduce(Max::of).get();
    if (min.equals(max))
      return Array.zeros(vector.length());
    Scalar factor = max.subtract(min).invert();
    return vector.map(scalar -> scalar.subtract(min).multiply(factor));
  }
}
