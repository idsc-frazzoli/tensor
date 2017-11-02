// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** the purpose of AbsSquared is to preserve the precision when working with complex numbers.
 * Since {@link ComplexScalar}::abs involves a sqrt the square of abs is better computed using
 * <code>z * conjugate(z)</code>.
 * 
 * if a {@link Scalar} does not implement {@link ComplexEmbedding}
 * the function AbsSquared is computed simply as
 * <code>abs(x) ^ 2</code> */
public enum AbsSquared implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ConjugateInterface)
      return scalar.multiply(Conjugate.FUNCTION.apply(scalar));
    Scalar abs = scalar.abs();
    return abs.multiply(abs);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their absolute value */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
