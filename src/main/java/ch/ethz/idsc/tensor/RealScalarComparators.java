// code by jph
package ch.ethz.idsc.tensor;

import java.util.Comparator;

import ch.ethz.idsc.tensor.alg.Sort;

public class RealScalarComparators {
  /** ascending is default ordering when using {@link Sort} */
  public static final Comparator<RealScalar> ASCENDING = new Comparator<RealScalar>() {
    @Override
    public int compare(RealScalar a, RealScalar b) {
      return a.compareTo(b);
    }
  };
  public static final Comparator<RealScalar> DESCENDING = new Comparator<RealScalar>() {
    @Override
    public int compare(RealScalar a, RealScalar b) {
      return b.compareTo(a);
    }
  };

  // class cannot be instantiated
  private RealScalarComparators() {
  }
}
