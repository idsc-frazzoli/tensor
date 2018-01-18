// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Times.html">Times</a> */
public enum Times {
  ;
  /** function computes the product of a sequence of {@link Scalar}s
   * 
   * @param scalars
   * @return product of scalars, or {@link RealScalar#ONE} if no scalars are present */
  public static Scalar of(Scalar... scalars) {
    return Stream.of(scalars).reduce(Scalar::multiply).orElse(RealScalar.ONE);
  }
}
