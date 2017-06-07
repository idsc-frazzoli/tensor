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
 * <a href="https://reference.wolfram.com/language/ref/Sinh.html">Sinh</a> */
public enum Sinh implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.sinh(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      double re = z.real().number().doubleValue();
      double im = z.imag().number().doubleValue();
      return ComplexScalar.of( //
          Math.sinh(re) * Math.cos(im), //
          Math.cosh(re) * Math.sin(im));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their sinh */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Sinh.function);
  }
}
