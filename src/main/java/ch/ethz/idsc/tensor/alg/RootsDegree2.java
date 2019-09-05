// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ enum RootsDegree2 {
  ;
  static final Scalar N1_2 = RationalScalar.HALF.negate();

  /** @param coeffs {a, b, c} representing a + b*x + c*x^2 == 0
   * @return vector of length 2 with the roots as entries
   * if the two roots are real, then the smaller root is the first entry */
  static Tensor of(Tensor coeffs) {
    Scalar c = coeffs.Get(2);
    Scalar p = coeffs.Get(1).divide(c).multiply(N1_2);
    Scalar p2 = p.multiply(p);
    Scalar q = coeffs.Get(0).divide(c);
    Scalar d = Sqrt.FUNCTION.apply(p2.subtract(q));
    return Tensors.of(p.subtract(d), p.add(d));
  }
}
