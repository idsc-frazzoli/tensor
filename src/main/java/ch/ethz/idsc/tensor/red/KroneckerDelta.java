// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Arrays;
import java.util.Collection;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

/** not completely consistent with Mathematica since
 * Mathematica::KroneckerDelta[ ] == 1 (<-consistent)
 * Mathematica::KroneckerDelta[1] == 0 (inconsistent) */
public enum KroneckerDelta {
  ;
  /** @param objects
   * @return RealScalar.ONE if there are no two objects are distinct */
  public static Scalar of(Object... objects) {
    return of(Arrays.asList(objects));
  }

  /** @param collection
   * @return RealScalar.ONE if there are no two objects in the collection that are distinct */
  public static Scalar of(Collection<Object> collection) {
    return collection.stream().distinct().count() <= 1 ? RealScalar.ONE : ZeroScalar.get();
  }
}
