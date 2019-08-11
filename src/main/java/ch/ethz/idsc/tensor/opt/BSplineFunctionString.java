// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;

/** function defined over the interval [0, control.length() - 1] */
/* package */ class BSplineFunctionString extends BSplineFunction {
  /** index of last control point */
  private final int last;
  /** domain of this function */
  private final Clip domain;
  /** clip for knots */
  private final Clip clip;

  public BSplineFunctionString(int degree, Tensor control) {
    super(degree, control);
    last = control.length() - 1;
    domain = Clips.positive(last);
    clip = Clips.interval( //
        domain.min().add(shift), //
        domain.max().add(shift));
  }

  @Override // from BSplineFunction
  Scalar domain(Scalar scalar) {
    return domain.requireInside(scalar);
  }

  @Override // from BSplineFunction
  Tensor knots(Tensor knots) {
    return knots.map(clip);
  }

  @Override // from BSplineFunction
  int bound(int index) {
    return Math.min(Math.max(0, index), last);
  }
}
