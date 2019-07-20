// code by jph
// adapted from https://en.wikipedia.org/wiki/De_Boor%27s_algorithm
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** DeBoor denotes the function that is defined by control points over a sequence of knots. */
public class DeBoor implements ScalarTensorFunction {
  /** @param knots vector of length degree * 2
   * @param control points of length degree + 1
   * @return
   * @throws Exception if given knots is not a vector, or degree cannot be established */
  public static DeBoor of(Tensor knots, Tensor control) {
    int length = knots.length();
    int degree = length / 2;
    if (length % 2 == 0 && //
        control.length() == degree + 1)
      return new DeBoor(degree, VectorQ.require(knots), control);
    throw TensorRuntimeException.of(knots, control);
  }

  // ---
  private final int degree;
  private final Tensor knots;
  private final Tensor control;

  /** @param degree
   * @param knots vector of length degree * 2
   * @param control points of length degree + 1 */
  /* package */ DeBoor(int degree, Tensor knots, Tensor control) {
    this.degree = degree;
    this.knots = knots;
    this.control = control;
  }

  @Override
  public Tensor apply(Scalar x) {
    Tensor d = control.copy(); // d is modified over the course of the algorithm
    for (int r = 1; r < degree + 1; ++r)
      for (int j = degree; j >= r; --j) {
        Scalar kj1 = knots.Get(j - 1); // knots max index = degree - 1
        Scalar num = x.subtract(kj1);
        Scalar den = knots.Get(j + degree - r).subtract(kj1); // knots max index = degree + degree - 1
        Scalar alpha = Scalars.isZero(den) //
            ? RealScalar.ZERO
            : num.divide(den);
        Tensor a0 = d.get(j - 1).multiply(RealScalar.ONE.subtract(alpha)); // control max index = degree - 1
        d.set(dj -> dj.multiply(alpha).add(a0), j); // control max index = degree
      }
    return d.get(degree); // control max index = degree
  }

  public int degree() {
    return degree;
  }

  public Tensor control() {
    return control.unmodifiable();
  }

  public Tensor knots() {
    return knots.unmodifiable();
  }
}
