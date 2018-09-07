// code by jph
package ch.ethz.idsc.tensor;

import java.util.Comparator;

import ch.ethz.idsc.tensor.alg.Sort;

/** IMPLEMENTATION TO BE USED ONLY IN TESTS
 * 
 * for canonic and reverse sorting...
 * 
 * perhaps Java already offers something similar? */
public enum Comparators {
  ;
  /** ascending is default ordering when using {@link Sort}
   * @see Comparator#naturalOrder
   * 
   * @return comparator that performs canonic ordering */
  public static final <T> Comparator<T> increasing() {
    return new Comparator<T>() {
      @Override
      public int compare(T a, T b) {
        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) a;
        return comparable.compareTo(b);
      }
    };
  }

  /** @return comparator that performs reversed canonic ordering */
  public static final <T> Comparator<T> decreasing() {
    return new Comparator<T>() {
      @Override
      public int compare(T a, T b) {
        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) b;
        return comparable.compareTo(a);
      }
    };
  }
}
