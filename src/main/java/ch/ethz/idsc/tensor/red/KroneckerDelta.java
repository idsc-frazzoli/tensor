// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

/** not completely consistent with Mathematica since
 * Mathematica::KroneckerDelta[ ] == 1 (<-consistent)
 * Mathematica::KroneckerDelta[1] == 0 (inconsistent)
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/KroneckerDelta.html">KroneckerDelta</a> */
public enum KroneckerDelta {
  ;
  // ---
  /** @param scalar
   * @return function that maps input to 1 if input matches scalar, otherwise gives 0 */
  public static Function<Scalar, Scalar> function(Scalar scalar) {
    return value -> of(scalar, value);
  }

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
