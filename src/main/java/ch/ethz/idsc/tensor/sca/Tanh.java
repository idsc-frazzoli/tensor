// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Sinh[z]/Cosh[z] == Tanh[z] == (Exp[z] - Exp[-z])/(Exp[z] + Exp[-z])
 * 
 * tanh(x + y) = F(tanh(x), tanh(y))
 * 
 * F(x,y) = (x + y)/(1 + xy)
 * 
 * formula for addition of velocities in special relativity (with the speed of light equal to 1)
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tanh.html">Tanh</a> */
public enum Tanh implements ScalarUnaryOperator {
  function;
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.tanh(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return Sinh.function.apply(z).divide(Cosh.function.apply(z));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their tanh */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Tanh.function);
  }
}
