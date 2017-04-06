// code by jph
package ch.ethz.idsc.tensor;

import java.util.Comparator;

import ch.ethz.idsc.tensor.alg.Sort;

public enum ScalarComparators {
  ;
  /** ascending is default ordering when using {@link Sort} */
  public static final Comparator<Scalar> ASCENDING = new Comparator<Scalar>() {
    @Override
    public int compare(Scalar a, Scalar b) {
      @SuppressWarnings("unchecked")
      Comparable<Scalar> comparable = (Comparable<Scalar>) a;
      return comparable.compareTo(b);
    }
  };
  public static final Comparator<Scalar> DESCENDING = new Comparator<Scalar>() {
    // TODO try to use template notation
    @Override
    public int compare(Scalar a, Scalar b) {
      @SuppressWarnings("unchecked")
      Comparable<Scalar> comparable = (Comparable<Scalar>) b;
      return comparable.compareTo(a);
    }
  };
}
