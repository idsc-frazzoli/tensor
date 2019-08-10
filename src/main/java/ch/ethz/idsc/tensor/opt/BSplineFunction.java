// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
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
public abstract class BSplineFunction implements ScalarTensorFunction {
  /** the control point are stored by reference, i.e. modifications to
   * given tensor alter the behavior of this BSplineFunction instance.
   * 
   * @param degree of polynomial basis function, non-negative integer
   * @param control points with at least one element
   * @return
   * @throws Exception if degree is negative, or control does not have length at least one */
  public static ScalarTensorFunction string(int degree, Tensor control) {
    return new BSplineFunctionString(StaticHelper.requirePositiveOrZero(degree), control);
  }

  /** function is periodic every interval [0, control.length())
   * 
   * @param degree non-negative
   * @param control with at least one element
   * @return function defined for all real scalars not constrained to a finite interval
   * @throws Exception if degree is negative, or control does not have length at least one */
  public static ScalarTensorFunction cyclic(int degree, Tensor control) {
    return new BSplineFunctionCyclic(StaticHelper.requirePositiveOrZero(degree), control);
  }

  // ---
  private final int degree;
  private final Tensor control;
  /** half == degree / 2 */
  private final int half;
  /** shift is 0 for odd degree and 1/2 for even degree */
  final Scalar shift;

  BSplineFunction(int degree, Tensor control) {
    this.degree = degree;
    this.control = control;
    half = degree / 2;
    shift = degree % 2 == 0 //
        ? RationalScalar.HALF
        : RealScalar.ZERO;
  }

  @Override // from ScalarTensorFunction
  public final Tensor apply(Scalar scalar) {
    scalar = domain(scalar).add(shift);
    return deBoor(Floor.FUNCTION.apply(scalar).number().intValue()).apply(scalar);
  }

  /** @param k
   * @return */
  public final DeBoor deBoor(int k) {
    int hi = degree + 1 + k;
    return new DeBoor( //
        LinearBinaryAverage.INSTANCE, //
        degree, //
        knots(Range.of(-degree + 1 + k, hi)), //
        Tensor.of(IntStream.range(k - half, hi - half) // control
            .map(this::bound) //
            .mapToObj(control::get)));
  }

  /** @param scalar
   * @return scalar in evaluation domain
   * @throws Exception if scalar is outside defined domain */
  abstract Scalar domain(Scalar scalar);

  /** @param knots
   * @return */
  abstract Tensor knots(Tensor knots);

  /** @param index
   * @return */
  abstract int bound(int index);
}
