// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Floor;

/** The implementation of BSplineFunction in the tensor library
 * is different from Mathematica.
 * 
 * tensor::BSplineFunction is parameterized over the interval
 * [0, control.length()-1]
 * 
 * tensor::BSplineFunction can be instantiated for all degrees
 * regardless of the length of the control points.
 * 
 * Mathematica::BSplineFunction throws an exception if number
 * of control points is insufficient for the specified degree.
 * 
 * <p>Quote from Wikipedia:
 * The term "B-spline" was coined by Isaac Jacob Schoenberg and is short for basis spline.
 * A spline is a piecewise polynomial function of a given degree in a variable x.
 * The values of x where the pieces of polynomial meet are known as knots, denoted
 * ..., t0, t1, t2, ... and sorted into non-decreasing order. */
public class BSplineFunction implements ScalarTensorFunction {
  /** the control point are stored by reference, i.e. modifications to
   * given tensor alter the behavior of this BSplineFunction instance.
   * 
   * @param degree of polynomial basis function, non-negative integer
   * @param control points with at least one element
   * @return */
  public static BSplineFunction of(int degree, Tensor control) {
    if (degree < 0)
      throw new IllegalArgumentException(Integer.toString(degree));
    return new BSplineFunction(degree, control);
  }

  // ---
  private final int degree;
  private final Tensor control;
  /** half == degree / 2 */
  private final int half;
  /** shift is 0 for odd degree and 1/2 for even degree */
  private final Scalar shift;
  /** index of last control point */
  private final int last;
  /** domain of this function */
  private final Clip domain;
  /** clip for knots */
  private final Clip clip;

  private BSplineFunction(int degree, Tensor control) {
    this.degree = degree;
    this.control = control;
    half = degree / 2;
    shift = degree % 2 == 0 //
        ? RationalScalar.HALF
        : RealScalar.ZERO;
    last = control.length() - 1;
    domain = Clips.positive(last);
    clip = Clips.interval( //
        domain.min().add(shift), //
        domain.max().add(shift));
  }

  /** @param scalar inside interval [0, control.length() - 1]
   * @return
   * @throws Exception if given scalar is outside required interval */
  @Override
  public Tensor apply(Scalar scalar) {
    scalar = domain.requireInside(scalar).add(shift);
    return deBoor(Floor.FUNCTION.apply(scalar).number().intValue()).apply(scalar);
  }

  /** @param k in the interval [0, control.length() - 1]
   * @return */
  public DeBoor deBoor(int k) {
    int hi = degree + 1 + k;
    return new DeBoor(degree, //
        Range.of(-degree + 1 + k, hi).map(clip), // knots
        Tensor.of(IntStream.range(k - half, hi - half) // control
            .map(this::bound) //
            .mapToObj(control::get)));
  }

  // helper function
  private int bound(int index) {
    return Math.min(Math.max(0, index), last);
  }
}
