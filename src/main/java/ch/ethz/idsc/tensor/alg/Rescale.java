// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
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
  /** Example:
   * Rescale[{10, 20, 30}] == {0, 1/2, 1}
   * 
   * @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    if (tensor.isScalar())
      throw TensorRuntimeException.of(tensor);
    if (tensor.length() == 0)
      return Tensors.empty();
    Scalar min = tensor.flatten(-1).map(Scalar.class::cast).reduce(Min::of).get();
    Scalar max = tensor.flatten(-1).map(Scalar.class::cast).reduce(Max::of).get();
    if (min.equals(max))
      return tensor.map(scalar -> ZeroScalar.get()); // set all entries to 0
    Scalar factor = max.subtract(min).invert();
    return tensor.map(scalar -> scalar.subtract(min).multiply(factor));
  }
}
