// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Factorial.html">Factorial</a> */
public enum Factorial implements ScalarUnaryOperator {
  function;
  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.intValueExact(scalar) < 0)
      throw TensorRuntimeException.of(scalar);
    Scalar product = RealScalar.ONE;
    while (!scalar.equals(RealScalar.ZERO)) {
      product = product.multiply(scalar);
      scalar = Decrement.ONE.apply(scalar);
    }
    return product;
  }
}
