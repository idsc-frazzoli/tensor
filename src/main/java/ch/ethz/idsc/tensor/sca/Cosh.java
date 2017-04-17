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
 * <a href="https://reference.wolfram.com/language/ref/Cosh.html">Cosh</a> */
public enum Cosh implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      return DoubleScalar.of(Math.cosh(value));
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      double re = z.real().number().doubleValue();
      double im = z.imag().number().doubleValue();
      return ComplexScalar.of( //
          Math.cosh(re) * Math.cos(im), //
          Math.sinh(re) * Math.sin(im));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their cosh */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Cosh.function);
  }
}
