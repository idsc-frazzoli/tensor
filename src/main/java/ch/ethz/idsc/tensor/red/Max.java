// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BinaryOperator;
import java.util.function.Function;

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

  public static <T> Function<T, T> with(T a) {
    return b -> of(a, b);
  }
}
