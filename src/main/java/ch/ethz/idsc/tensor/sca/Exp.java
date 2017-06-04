// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** gives the exponential
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Exp.html">Exp</a> */
public enum Exp implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.exp(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return ComplexScalar.fromPolar(apply(z.real()), z.imag()); // construct in polar coordinates
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their exponential */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Exp.function);
  }
}
