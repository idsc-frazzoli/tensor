// code by jph
// adapted from https://en.wikipedia.org/wiki/De_Boor%27s_algorithm
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** Implementation is deliberately not optimized
 * 
 * @param x
 * @return control point evaluated at x */
// EXPERIMENTAL
public class DeBoor implements ScalarTensorFunction {
  static Tensor of(int p, Tensor c, Tensor t, Scalar x) {
    return new DeBoor(p, c, t).apply(x);
  }

  // ---
  private final int p;
  private final Tensor c;
  private final Tensor t;

  /** @param p degree, non-negative integer
   * @param c control points, tensor of length p + 1
   * @param t knot position, vector of length p * 2 */
  public DeBoor(int p, Tensor c, Tensor t) {
    this.p = p;
    this.c = c;
    this.t = t;
  }

  @Override
  public Tensor apply(Scalar x) {
    Tensor d = c.copy(); // d is modified over the course of the algorithm
    for (int r = 1; r < p + 1; ++r)
      for (int j = p; j >= r; --j) {
        Scalar num = x.subtract(t.Get(j - 1));
        Scalar den = t.Get(j + p - r).subtract(t.Get(j - 1));
        Scalar alpha = Scalars.isZero(den) ? num : num.divide(den);
        Tensor a0 = d.get(j - 1).multiply(RealScalar.ONE.subtract(alpha));
        d.set(dj -> dj.multiply(alpha).add(a0), j);
      }
    return d.get(p);
  }

  public int degree() {
    return p;
  }

  public Tensor control() {
    return c.unmodifiable();
  }

  public Tensor knots() {
    return t.unmodifiable();
  }
}
