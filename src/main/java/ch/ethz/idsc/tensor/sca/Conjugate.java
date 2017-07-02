// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.mat.ConjugateTranspose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Conjugate.html">Conjugate</a> */
public enum Conjugate implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ComplexEmbedding) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      return complexEmbedding.conjugate();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** see also {@link ConjugateTranspose}
   * 
   * @param tensor
   * @return tensor with all entries conjugated
   * @see ConjugateTranspose */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
