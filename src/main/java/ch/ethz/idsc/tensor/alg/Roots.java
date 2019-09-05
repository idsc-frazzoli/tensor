// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** Not entirely consistent with Mathematica for the case
 * Mathematica::Roots[a == 0, x] == false
 * Tensor::Roots[a == 0, x] == {}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Roots.html">Roots</a> */
public enum Roots {
  ;
  /** attempts to find all roots of a polynomial
   * 
   * <pre>
   * Roots.of(coeffs).map(Series.of(coeffs)) == {0, 0, ...}
   * </pre>
   * 
   * @param coeffs of polynomial, for instance {a, b, c, d} represents
   * cubic polynomial a + b*x + c*x^2 + d*x^3
   * @return roots of polynomial as vector with length of that of coeffs minus one
   * @throws Exception given coeffs vector is empty
   * @throws Exception if roots cannot be determined
   * @see Series */
  public static Tensor of(Tensor coeffs) {
    int degree = coeffs.length() - 1;
    if (Scalars.isZero(coeffs.Get(degree)))
      return of(coeffs.extract(0, degree));
    switch (degree) {
    case 0:
      return Tensors.empty();
    case 1: // a + b*x == 0
      return linear(coeffs);
    }
    if (Scalars.isZero(coeffs.Get(0))) {
      Tensor roots = of(coeffs.extract(1, coeffs.length()));
      return roots.append(roots.Get(0).zero());
    }
    switch (degree) {
    case 2: // a + b*x + c*x^2 == 0
      return RootsDegree2.of(coeffs);
    case 3: // a + b*x + c*x^2 + d*x^3 == 0
      return RootsDegree3.of(coeffs);
    }
    throw TensorRuntimeException.of(coeffs);
  }

  /** @param coeffs {a, b} representing a + b*x == 0
   * @return vector of length 1 */
  private static Tensor linear(Tensor coeffs) {
    return Tensors.of(coeffs.Get(0).divide(coeffs.Get(1)).negate());
  }
}
