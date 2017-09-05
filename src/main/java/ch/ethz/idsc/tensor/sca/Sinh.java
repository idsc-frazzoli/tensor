// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** http://www.milefoot.com/math/complex/functionsofi.htm
 *
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sinh.html">Sinh</a> */
public enum Sinh implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof TrigonometryInterface) {
      TrigonometryInterface trigonometryInterface = (TrigonometryInterface) scalar;
      return trigonometryInterface.sinh();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their sinh */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
