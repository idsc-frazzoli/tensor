// code by jph
// adapted from https://en.wikipedia.org/wiki/De_Boor%27s_algorithm
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum DeBoor {
  ;
  /** @param p degree
   * @param c control points
   * @param t knot position
   * @param x
   * @return control point evaluated at x */
  static Tensor of(int p, Tensor c, Tensor t, Scalar x) {
    int k = p - 1;
    Tensor d = c.copy(); // d is modified over the course of the algorithm
    for (int r = 1; r < p + 1; ++r) {
      for (int j = p; j >= r; --j) {
        Scalar num = x.subtract(t.Get(j + k - p));
        Scalar den = t.Get(j + 1 + k - r).subtract(t.Get(j + k - p));
        Scalar alpha = Scalars.isZero(den) ? num : num.divide(den);
        Tensor a0 = d.get(j - 1).multiply(RealScalar.ONE.subtract(alpha));
        Tensor a1 = d.get(j).multiply(alpha);
        d.set(a0.add(a1), j);
      }
    }
    return d.get(p);
  }
}
