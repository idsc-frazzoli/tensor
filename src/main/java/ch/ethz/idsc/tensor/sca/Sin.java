// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** http://www.milefoot.com/math/complex/functionsofi.htm
 *
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sin.html">Sin</a> */
public enum Sin implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.sin(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      double re = z.real().number().doubleValue();
      double im = z.imag().number().doubleValue();
      return ComplexScalar.of( //
          Math.sin(re) * Math.cosh(im), //
          Math.cos(re) * Math.sinh(im));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their sin */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
