// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** maps a non-zero scalar to {@link RealScalar#ONE}, and a zero scalar to {@link RealScalar#ZERO}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Unitize.html">Unitize</a> */
public enum Unitize implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return Scalars.isZero(scalar) ? RealScalar.ZERO : RealScalar.ONE;
  }

  /** @param tensor
   * @return tensor with all scalars unitized */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
