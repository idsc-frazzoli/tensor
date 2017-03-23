// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** gives the argument of the complex number
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Arg.html">Arg</a> */
public enum Arg implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      RealScalar realScalar = (RealScalar) scalar;
      if (realScalar.compareTo(ZeroScalar.get()) < 0)
        return DoubleScalar.of(Math.PI);
      return ZeroScalar.get();
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return DoubleScalar.of(Math.atan2( //
          complexScalar.imag().number().doubleValue(), //
          complexScalar.real().number().doubleValue() //
      ));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their argument */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Arg.function);
  }
}
