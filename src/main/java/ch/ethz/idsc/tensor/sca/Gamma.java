// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** implementation only works for positive integers
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Gamma.html">Gamma</a> */
public enum Gamma implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    // TODO treat cases n+1/2, etc.
    return Factorial.of(scalar.subtract(RealScalar.ONE));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their gamma evaluation */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
