// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** <p>ordering of coefficients is <em>reversed</em> compared to
 * MATLAB::polyval, MATLAB::polyfit, etc. ! */
/* package */ enum Multinomial {
  ;
  // ---
  /** horner scheme
   * 
   * @param coeffs
   * @param scalar
   * @return */
  /* package */ static Scalar horner(Tensor coeffs, Scalar scalar) {
    Scalar total = ZeroScalar.get();
    for (Tensor entry : Reverse.of(coeffs))
      total = total.multiply(scalar).add(entry);
    return total;
  }
}
