// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Im.html">Im</a> */
public enum Imag implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return ZeroScalar.get();
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return complexScalar.imag();
    }
    throw new UnsupportedOperationException("imag " + scalar.getClass().getName());
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their imaginary part */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Imag.function::apply);
  }
}
