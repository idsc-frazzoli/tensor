// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Floor.html">Floor</a> */
public enum Floor implements Function<Scalar, Scalar> {
  function;
  // ---
  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to floor of scalar
   * @throws TensorRuntimeException if scalar is Infinity, or NaN */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof FloorInterface) {
      FloorInterface floorInterface = (FloorInterface) scalar;
      return floorInterface.floor();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their floor */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Floor.function);
  }
}
