// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** <pre>
 * Tanh[z] == Sinh[z]/Cosh[z]
 * Tanh[z] == (Exp[z] - Exp[-z])/(Exp[z] + Exp[-z])
 * </pre>
 * 
 * <pre>
 * Tanh(x + y) = F(Tanh(x), tanh(y))
 * F(x,y) = (x + y)/(1 + xy)
 * </pre>
 * 
 * <p>formula for addition of velocities in special relativity (with the speed of light equal to 1)
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tanh.html">Tanh</a> */
public enum Tanh implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.tanh(scalar.number().doubleValue()));
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return Sinh.FUNCTION.apply(z).divide(Cosh.FUNCTION.apply(z));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their tanh */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
