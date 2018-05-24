// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** <p>ordering of coefficients is <em>reversed</em> compared to
 * MATLAB::polyval, MATLAB::polyfit, etc. ! */
public enum Multinomial {
  ;
  /** the horner scheme improves speed and stability for the numeric evaluation of large polynomials
   * 
   * <pre>
   * horner({a, b, c, d}, x)
   * == a + b*x + c*x^2 + d*x^3
   * == a + x*(b + x*(c + x*(d)))
   * </pre>
   * 
   * @param coeffs of polynomial
   * @return evaluation of polynomial for scalar input */
  public static ScalarUnaryOperator horner(Tensor coeffs) {
    return new HornerScheme(coeffs);
  }

  /** Example:
   * <pre>
   * derivative({a, b, c, d}) == {b, 2*c, 3*d}
   * </pre>
   * 
   * @param coeffs
   * @return coefficients of polynomial that is the derivative of the polynomial defined by given coeffs */
  // API not final
  public static Tensor derivative(Tensor coeffs) {
    int length = coeffs.length();
    return length == 0 //
        ? Tensors.empty()
        : Range.of(1, length).pmul(coeffs.extract(1, length));
  }
}
