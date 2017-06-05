// code by jph
package ch.ethz.idsc.tensor.sca;

import java.io.Serializable;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Clip.html">Clip</a> */
public class Clip implements Function<Scalar, Scalar>, Serializable {
  /** @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max] */
  public static Clip function(Number min, Number max) {
    return function(RealScalar.of(min), RealScalar.of(max));
  }

  /** @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max] */
  public static Clip function(Scalar min, Scalar max) {
    return new Clip(min, max);
  }

  private final Scalar min;
  private final Scalar max;

  private Clip(Scalar min, Scalar max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.lessThan(scalar, min))
      return min;
    if (Scalars.lessThan(max, scalar))
      return max;
    return scalar;
  }
}
