// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Round;

/** entries of index are rounded to nearest integers */
public class NearestInterpolation extends MappedInterpolation {
  /** @param tensor
   * @return */
  public static Interpolation of(Tensor tensor) {
    return new NearestInterpolation(tensor);
  }

  /* package */ NearestInterpolation(Tensor tensor) {
    super(tensor, Round::of);
  }
}
