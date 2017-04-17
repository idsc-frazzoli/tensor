// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** ArcTanh[z] == 1/2 (log(1+z)-log(1-z))
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcTanh.html">ArcTanh</a> */
public enum ArcTanh implements Function<Scalar, Scalar> {
  function;
  // ---
  private static final Scalar HALF = RationalScalar.of(1, 2);

  @Override
  public Scalar apply(Scalar scalar) {
    return HALF.multiply( //
        Log.function.apply(RealScalar.ONE.add(scalar)).subtract(Log.function.apply(RealScalar.ONE.subtract(scalar))));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc tan */
  public static Tensor of(Tensor tensor) {
    return tensor.map(ArcTanh.function);
  }
}
