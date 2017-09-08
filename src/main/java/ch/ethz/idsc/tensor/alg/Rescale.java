// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Optional;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** Rescale so that all the list elements run from 0 to 1
 * 
 * <code>
 * Rescale[{-.7, .5, 1.2, 5.6, 1.8}] == {0., 0.190476, 0.301587, 1., 0.396825}
 * </code>
 * 
 * Mathematica handles Infinity in a non-trivial way.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Rescale.html">Rescale</a> */
public enum Rescale {
  ;
  private static ScalarUnaryOperator NUMBERQ_ZERO = scalar -> NumberQ.of(scalar) ? scalar.zero() : scalar;

  /** Example:
   * Rescale[{10, 20, 30}] == {0, 1/2, 1}
   * 
   * @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    if (tensor.length() == 0)
      return Tensors.empty();
    Optional<Scalar> optional = tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(NumberQ::of) //
        .reduce(Min::of);
    if (!optional.isPresent())
      return tensor.map(NUMBERQ_ZERO); // set all number entries to 0
    // ---
    Scalar min = optional.get();
    Scalar max = tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(NumberQ::of) //
        .reduce(Max::of).get(); // if a minimum exists, then there exists a maximum
    if (min.equals(max))
      return tensor.map(NUMBERQ_ZERO); // set all number entries to 0
    Scalar factor = max.subtract(min);
    return tensor.map(scalar -> scalar.subtract(min).divide(factor));
  }
}
