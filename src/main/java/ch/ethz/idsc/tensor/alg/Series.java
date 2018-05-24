// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Series.html">Series</a> */
public enum Series {
  ;
  /** polynomial evaluation
   * 
   * <pre>
   * Series.of({a, b, c, d}).apply(x)
   * == a + b*x + c*x^2 + d*x^3
   * == a + x*(b + x*(c + x*(d)))
   * </pre>
   * 
   * @param coeffs of polynomial
   * @return evaluation of polynomial for scalar input */
  public static ScalarUnaryOperator of(Tensor coeffs) {
    return new HornerScheme(coeffs);
  }
}
