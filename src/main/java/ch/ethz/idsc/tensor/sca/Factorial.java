// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** the tensor library defines factorial only for non-negative integers
 * 
 * Mathematica::Factorial also works on decimals, for instance
 * 3.21! == 7.85918
 * 
 * Mathematica::FunctionExpand[x!] == Gamma[1 + x]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Factorial.html">Factorial</a> */
public enum Factorial implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.intValueExact(scalar) < 0)
      throw TensorRuntimeException.of(scalar);
    // TODO use memo function
    Scalar product = RealScalar.ONE;
    while (!scalar.equals(RealScalar.ZERO)) {
      product = product.multiply(scalar);
      scalar = Decrement.ONE.apply(scalar);
    }
    return product;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their factorial */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
