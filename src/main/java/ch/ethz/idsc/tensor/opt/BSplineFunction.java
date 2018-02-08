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
   * @param tensor */
  public static BSplineFunction of(int degree, Tensor tensor) {
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

  private BSplineFunction(int degree, Tensor tensor) {
    p = degree;
    this.tensor = tensor;
    index = Range.of(-p + 1, p + 1);
    last = tensor.length() - 1;
    clip = Clip.function(0, last);
    ofs = p / 2;
    shift = degree % 2 == 0 //
        ? RationalScalar.HALF
        : RealScalar.ZERO;
  }

  @Override
  public Tensor apply(Scalar scalar) {
    clip.requireInside(scalar);
    scalar = scalar.add(shift);
    Scalar lo = Floor.FUNCTION.apply(scalar);
    int k = lo.number().intValue();
    Tensor ctr = Tensor.of(IntStream.range(k - ofs, k + p + 1 - ofs) //
        .map(i -> Math.min(Math.max(0, i), last)).mapToObj(tensor::get));
    Tensor t = index.map(s -> s.add(lo)).map(clip);
    // System.out.println(scalar);
    // System.out.println(t);
    // System.out.println(ctr);
    return DeBoor.of(p, ctr, t, scalar);
  }
}
