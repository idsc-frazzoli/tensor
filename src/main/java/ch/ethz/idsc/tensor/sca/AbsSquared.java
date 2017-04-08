// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** the purpose of AbsSquared is to preserve the precision when working with complex numbers.
 * Since {@link ComplexScalar}::abs involves a sqrt the square of abs is better computed using
 * <code>z * conjugate(z)</code>.
 * 
 * if a {@link Scalar} does not implement {@link ConjugateInterface}
 * the function AbsSquared is computed simply as
 * <code>abs(x) ^ 2</code> */
public enum AbsSquared implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ConjugateInterface)
      return scalar.multiply(Conjugate.function.apply(scalar));
    Scalar abs = scalar.abs();
    return abs.multiply(abs);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their absolute value */
  public static Tensor of(Tensor tensor) {
    return tensor.map(AbsSquared.function);
  }
}
