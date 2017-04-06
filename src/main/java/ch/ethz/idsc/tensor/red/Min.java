// code by jph
package ch.ethz.idsc.tensor.red;

public enum Min {
  ;
  /** @param a
   * @param b
   * @return the smaller one among a and b */
  public static <T> T of(T a, T b) {
    @SuppressWarnings("unchecked")
    Comparable<T> comparable = (Comparable<T>) a;
    return comparable.compareTo(b) > 0 ? b : a;
  }
}
