// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.num.GaussScalar;

/** implementation permits coefficients of type {@link GaussScalar} */
/* package */ enum RootsDegree1 {
  ;
  /** @param coeffs {a, b} representing a + b*x == 0
   * @return vector of length 1 */
  public static Tensor of(Tensor coeffs) {
    return Tensors.of(coeffs.Get(0).divide(coeffs.Get(1)).negate());
  }
}
