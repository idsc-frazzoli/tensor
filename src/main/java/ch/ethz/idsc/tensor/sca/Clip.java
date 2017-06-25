// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Clip.html">Clip</a> */
public class Clip implements ScalarUnaryOperator {
  /** clip function that maps to the unit interval [0, 1] */
  public static final Clip UNIT = function(RealScalar.ZERO, RealScalar.ONE);
  /** clip function that clips scalars to the interval [-1, 1] */
  public static final Clip ABSOLUTE_ONE = function(RealScalar.NEGATIVE_ONE, RealScalar.ONE);

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
    if (Scalars.lessThan(max, min))
      throw TensorRuntimeException.of(min, max);
    return new Clip(min, max);
  }

  // ---
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

  @SuppressWarnings("unchecked")
  public <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }
}
