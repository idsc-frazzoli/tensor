// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

/** not completely consistent with Mathematica since
 * Mathematica::KroneckerDelta[ ] == 1 (<-consistent)
 * Mathematica::KroneckerDelta[1] == 0 (inconsistent) */
public class KroneckerDelta {
  public static Scalar of(Object... objects) {
    return of(Arrays.asList(objects));
  }

  public static Scalar of(List<Object> list) {
    return list.stream().distinct().count() <= 1 ? RealScalar.of(1) : ZeroScalar.get();
  }
}
