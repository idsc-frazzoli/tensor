// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;

/** FOR TESTING
 * 
 * ordering of coefficients is <em>reversed</em> compared to
 * MATLAB::polyval, MATLAB::polyfit, etc. ! */
enum Multinomial {
  ;
  /** Example:
   * <pre>
   * derivative({a, b, c, d}) == {b, 2*c, 3*d}
   * </pre>
   * 
   * @param coeffs
   * @return coefficients of polynomial that is the derivative of the polynomial defined by given coeffs */
  public static Tensor derivative(Tensor coeffs) {
    int length = coeffs.length();
    return length == 0 //
        ? Tensors.empty()
        : Range.of(1, length).pmul(coeffs.extract(1, length));
  }
}
