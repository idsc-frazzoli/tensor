// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/CentralMoment.html">CentralMoment</a> */
public enum CentralMoment {
  ;
  /** @param vector
   * @param order of moment
   * @return */
  public static Scalar of(Tensor vector, Scalar order) {
    Scalar mean = Mean.of(vector).Get();
    return vector.stream() //
        .map(Scalar.class::cast) //
        .map(scalar -> scalar.subtract(mean)) //
        .map(Power.function(order)) //
        .reduce(Scalar::add) //
        .get() //
        .divide(RationalScalar.of(vector.length(), 1));
  }

  /** @param vector
   * @param order of moment
   * @return */
  public static Scalar of(Tensor vector, Number order) {
    return of(vector, RealScalar.of(order));
  }
}
