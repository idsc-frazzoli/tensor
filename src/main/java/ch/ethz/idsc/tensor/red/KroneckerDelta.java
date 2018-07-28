// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** not completely consistent with Mathematica since
 * Mathematica::KroneckerDelta[ ] == 1 (<-consistent)
 * Mathematica::KroneckerDelta[1] == 0 (inconsistent)
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/KroneckerDelta.html">KroneckerDelta</a> */
public enum KroneckerDelta {
  ;
  /** @param scalar
   * @return function that maps input to 1 if input matches scalar, otherwise gives 0 */
  public static ScalarUnaryOperator function(Scalar scalar) {
    return value -> of(scalar, value);
  }

  /** @param objects
   * @return RealScalar.ONE if there are no two objects are distinct,
   * otherwise RealScalar.ZERO */
  public static Scalar of(Object... objects) {
    return of(Stream.of(objects));
  }

  /** @param stream
   * @return RealScalar.ONE if there are no two objects in the stream that are distinct,
   * otherwise RealScalar.ZERO */
  public static Scalar of(Stream<?> stream) {
    return stream.distinct().limit(2).count() <= 1 //
        ? RealScalar.ONE
        : RealScalar.ZERO;
  }
}
