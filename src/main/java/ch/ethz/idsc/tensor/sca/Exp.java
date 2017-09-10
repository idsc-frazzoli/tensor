// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** gives the exponential of a {@link Scalar} that implements {@link ExpInterface}.
 * Supported types include {@link RealScalar}, and {@link ComplexScalar}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Exp.html">Exp</a> */
public enum Exp implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ExpInterface) {
      ExpInterface expInterface = (ExpInterface) scalar;
      return expInterface.exp();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their exponential */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
