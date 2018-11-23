// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Floor;

/** Mathematica::BSplineFunction throws an exception if number
 * of control points is insufficient for the specified degree.
 * 
 * cubic basis functions over unit interval [0, 1]
 * {(1 - t)^3, 4 - 6 t^2 + 3 t^3, 1 + 3 t + 3 t^2 - 3 t^3, t^3}/6 */
// EXPERIMENTAL
public class BSplineFunction implements ScalarTensorFunction {
  /** the control point are stored by reference, i.e.
   * modifications to given tensor alter the behavior
   * of this BSplineFunction instance.
   * 
   * @param degree of polynomial basis function, non-negative integer
   * @param tensor of control points of length at least 2 */
  public static BSplineFunction of(int degree, Tensor tensor) {
    if (degree < 0)
      throw new IllegalArgumentException("" + degree);
    return new BSplineFunction(degree, tensor);
  }

  // ---
  private final int p;
  private final Tensor tensor;
  final Tensor index;
  private final int last;
  private final Clip clip;
  private final int ofs;
  private final Scalar shift;

  private BSplineFunction(int degree, Tensor control) {
    p = degree;
    this.tensor = control;
    index = Range.of(-p + 1, p + 1);
    last = control.length() - 1;
    clip = Clip.function(0, last);
    ofs = p / 2;
    shift = degree % 2 == 0 //
        ? RationalScalar.HALF
        : RealScalar.ZERO;
  }

  public DeBoor deBoor(Scalar scalar) {
    clip.requireInside(scalar);
    scalar = scalar.add(shift);
    Scalar lo = Floor.FUNCTION.apply(scalar);
    Tensor knots = index.map(s -> s.add(lo)).map(clip);
    int k = lo.number().intValue();
    Tensor control = Tensor.of(IntStream.range(k - ofs, k + p + 1 - ofs) //
        .map(i -> Math.min(Math.max(0, i), last)).mapToObj(tensor::get));
    return new DeBoor(p, knots, control);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return deBoor(scalar).apply(scalar.add(shift));
  }
}
