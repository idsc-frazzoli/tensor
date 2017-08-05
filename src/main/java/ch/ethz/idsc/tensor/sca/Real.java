// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Real projects a given scalar to its real part.
 * The scalar type is required to implement {@link ComplexEmbedding}
 * in order for the operation to succeed.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Re.html">Re</a> */
public enum Real implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ComplexEmbedding) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      return complexEmbedding.real();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their real part */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
