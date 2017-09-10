// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/** Example use:
 * <pre>
 * vector.flatten(0).reduce(Max::of).get();
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Max.html">Max</a> */
public enum Max {
  ;
  /** function is a {@link BinaryOperator} that can be used in reduce()
   * 
   * @param a
   * @param b
   * @return the greater one among a and b */
  public static <T> T of(T a, T b) {
    @SuppressWarnings("unchecked")
    Comparable<T> comparable = (Comparable<T>) a;
    return comparable.compareTo(b) < 0 ? b : a;
  }

  /** @param a
   * @return function that maps input to the greater one among input and a */
  public static <T> UnaryOperator<T> function(T a) {
    return b -> of(a, b);
  }
}
