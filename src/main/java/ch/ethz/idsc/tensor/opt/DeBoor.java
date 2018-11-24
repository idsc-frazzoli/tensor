// code by jph
// adapted from https://en.wikipedia.org/wiki/De_Boor%27s_algorithm
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** @param x
 * @return control point evaluated at x */
// EXPERIMENTAL
public class DeBoor implements ScalarTensorFunction {
  /** @param knots position, vector of length p * 2
   * @param control points, tensor of length p + 1
   * @return */
  public static DeBoor of(Tensor knots, Tensor control) {
    int p = knots.length() >> 1;
    if (control.length() != p + 1)
      throw TensorRuntimeException.of(knots, control);
    return new DeBoor(p, VectorQ.require(knots), control);
  }

  // ---
  private final int p;
  private final Tensor t;
  private final Tensor control;

  /** @param p degree
   * @param knots position, vector of length p * 2
   * @param control points, tensor of length p + 1 */
  /* package */ DeBoor(int p, Tensor knots, Tensor control) {
    this.p = p;
    this.t = knots;
    this.control = control;
  }

  @Override
  public Tensor apply(Scalar x) {
    Tensor d = control.copy(); // d is modified over the course of the algorithm
    for (int r = 1; r < p + 1; ++r)
      for (int j = p; j >= r; --j) {
        Scalar num = x.subtract(t.Get(j - 1));
        Scalar den = t.Get(j + p - r).subtract(t.Get(j - 1));
        // if (Scalars.isZero(den))
        // System.out.println("here:" + x);
        Scalar alpha = Scalars.isZero(den) //
            ? DoubleScalar.ZERO // ZERO vs. Nan?
            : num.divide(den);
        Tensor a0 = d.get(j - 1).multiply(RealScalar.ONE.subtract(alpha));
        d.set(dj -> dj.multiply(alpha).add(a0), j);
      }
    return d.get(p);
  }

  public int degree() {
    return p;
  }

  public Tensor control() {
    return control.unmodifiable();
  }

  public Tensor knots() {
    return t.unmodifiable();
  }
}
