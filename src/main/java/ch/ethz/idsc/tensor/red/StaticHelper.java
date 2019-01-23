// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/* package */ enum StaticHelper {
  ;
  public static Scalar normalForm(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return scalar.abs();
    return scalar;
  }
}
