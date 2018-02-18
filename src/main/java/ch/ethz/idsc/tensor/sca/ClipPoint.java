// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** clip to a single point */
/* package */ class ClipPoint extends ClipInterval {
  ClipPoint(Scalar value, Scalar width) {
    super(value, value, width);
  }

  @Override
  public Scalar rescale(Scalar scalar) {
    apply(scalar);
    return RealScalar.ZERO;
  }
}
