// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactPrecision;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** gives the exponential
 * 
 * <p>Exp.of(0) returns {@link ExactPrecision}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Exp.html">Exp</a> */
public enum Exp implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ZeroScalar)
      return RealScalar.ONE;
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.exp(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return ComplexScalar.fromPolar( // construct in polar coordinates
          apply(complexScalar.real()), //
          complexScalar.imag());
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their exponential */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Exp.function);
  }
}
