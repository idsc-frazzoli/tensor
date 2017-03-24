// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** http://www.milefoot.com/math/complex/functionsofi.htm
 *
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cos.html">Cos</a> */
public enum Cos implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      return DoubleScalar.of(Math.cos(value));
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      double re = complexScalar.real().number().doubleValue();
      double im = complexScalar.imag().number().doubleValue();
      return ComplexScalar.of( //
          Math.cos(re) * Math.cosh(im), //
          -Math.sin(re) * Math.sinh(im));
    }
    throw TensorRuntimeException.of(scalar);
  }

  public static Tensor of(Tensor tensor) {
    return tensor.map(Cos.function);
  }
}
