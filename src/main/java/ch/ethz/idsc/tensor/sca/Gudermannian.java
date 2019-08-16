// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.Pi;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Gudermannian.html">Gudermannian</a> */
public enum Gudermannian implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    Scalar value = ArcTan.FUNCTION.apply(Exp.FUNCTION.apply(scalar));
    return value.add(value).subtract(Pi.HALF);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their gudermannian evaluation */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
