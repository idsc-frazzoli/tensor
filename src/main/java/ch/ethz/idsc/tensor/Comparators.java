// code by jph
package ch.ethz.idsc.tensor;

import java.util.Comparator;

import ch.ethz.idsc.tensor.alg.Sort;

public enum Comparators {
  ;
  /** ascending is default ordering when using {@link Sort} */
  public static final <T> Comparator<T> ascending() {
    return new Comparator<T>() {
      @Override
      public int compare(T a, T b) {
        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) a;
        return comparable.compareTo(b);
      }
    };
  }

  /** @return comparator */
  public static final <T> Comparator<T> descending() {
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
