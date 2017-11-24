// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** the implementation guarantees an error smaller than 1.2 x 10^-7
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Erfc.html">Erfc</a> */
public enum Erfc implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return RealScalar.ONE.subtract(Erf.FUNCTION.apply(scalar));
  }

  /** @param tensor
   * @return tensor with all scalar entries replaced by the evaluation under Erfc */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}