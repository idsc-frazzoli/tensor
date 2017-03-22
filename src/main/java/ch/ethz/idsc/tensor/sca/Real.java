// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Re.html">Re</a> */
public enum Real implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return scalar;
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return complexScalar.real();
    }
    throw new UnsupportedOperationException("real " + scalar.getClass().getName());
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their real part */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Real.function::apply);
  }
}
