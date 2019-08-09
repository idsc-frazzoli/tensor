// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Mod;

public class CyclicBSplineFunction implements ScalarTensorFunction {
  /** function is periodic every interval [0, control.length())
   * 
   * @param degree non-negative
   * @param control with at least one element
   * @return function defined for all real scalars not constrained to a finite interval
   * @throws Exception if degree is negative, or control does not have length at least one */
  public static ScalarTensorFunction of(int degree, Tensor control) {
    return new CyclicBSplineFunction(StaticHelper.requirePositiveOrZero(degree), control);
  }

  // ---
  private final int degree;
  private final Tensor control;
  /** half == degree / 2 */
  private final int half;
  /** shift is 0 for odd degree and 1/2 for even degree */
  private final Scalar shift;
  /** periodic */
  private final Mod mod;

  private CyclicBSplineFunction(int degree, Tensor control) {
    this.degree = degree;
    this.control = control;
    half = degree / 2;
    shift = degree % 2 == 0 //
        ? RationalScalar.HALF
        : RealScalar.ZERO;
    mod = Mod.function(control.length());
  }

  /** @param scalar
   * @return
   * @throws Exception if given scalar is outside required interval */
  @Override
  public Tensor apply(Scalar scalar) {
    scalar = mod.apply(scalar).add(shift);
    return deBoor(Floor.FUNCTION.apply(scalar).number().intValue()).apply(scalar);
  }

  /** @param k in the interval [0, control.length()]
   * @return */
  public DeBoor deBoor(int k) {
    // LONGTERM could use specialized "uniform" deboor
    int hi = degree + 1 + k;
    return new DeBoor(degree, //
        Range.of(-degree + 1 + k, hi), // knots
        Tensor.of(IntStream.range(k - half, hi - half) // control
            .map(this::bound) //
            .mapToObj(control::get)));
  }

  // helper function
  private int bound(int index) {
    return Math.floorMod(index, control.length());
  }
}
