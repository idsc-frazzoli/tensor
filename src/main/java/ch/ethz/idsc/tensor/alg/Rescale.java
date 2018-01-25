// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Optional;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** Rescale so that all the list elements run from 0 to 1
 * 
 * <code>
 * Rescale[{-.7, .5, 1.2, 5.6, 1.8}] == {0., 0.190476, 0.301587, 1., 0.396825}
 * </code>
 * 
 * <p>Mathematica handles Infinity in a non-trivial way.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Rescale.html">Rescale</a> */
public enum Rescale {
  ;
  /** RealScalar.ZERO is used instead of {@link Scalar#zero()}
   * to eliminate unit of {@link Quantity}. */
  private static final ScalarUnaryOperator FINITE_NUMBER_ZERO = //
      scalar -> isFiniteNumber(scalar) ? RealScalar.ZERO : scalar;

  /** The scalar entries of the given tensor may also be instance of
   * {@link Quantity} with identical unit.
   * 
   * <p>Example:
   * <pre>
   * Rescale[{10, 20, 30}] == {0, 1/2, 1}
   * Rescale[{3[s], Infinity[s], 6[s], 2[s]}] == {1/4, Infinity, 1, 0}
   * </pre>
   * 
   * @param tensor of arbitrary structure
   * @return
   * @throws Exception if any entry is a {@link ComplexScalar} */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    Optional<Scalar> optional = tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(Rescale::isFiniteNumber) //
        .reduce(Min::of);
    if (!optional.isPresent())
      return tensor.map(FINITE_NUMBER_ZERO); // set all finite number entries to 0
    // ---
    Scalar min = optional.get();
    Scalar max = tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(Rescale::isFiniteNumber) //
        .reduce(Max::of).get(); // if a minimum exists, then there exists a maximum
    if (min.equals(max))
      return tensor.map(FINITE_NUMBER_ZERO); // set all finite number entries to 0
    Scalar factor = max.subtract(min);
    return tensor.map(scalar -> scalar.subtract(min).divide(factor));
  }

  // helper function
  private static boolean isFiniteNumber(Scalar scalar) {
    return Double.isFinite(scalar.number().doubleValue());
  }
}
