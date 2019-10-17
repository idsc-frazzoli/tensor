// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** Reference:
 * <a href="http://www.milefoot.com/math/complex/functionsofi.htm">functions of i</a>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcSinh.html">ArcSinh</a>
 * 
 * @see Sinh */
public enum ArcSinh implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    Scalar x2_o = Sqrt.FUNCTION.apply(scalar.multiply(scalar).add(RealScalar.ONE));
    return Log.FUNCTION.apply(scalar.add(x2_o)); // add or subtract
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc sinh */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
