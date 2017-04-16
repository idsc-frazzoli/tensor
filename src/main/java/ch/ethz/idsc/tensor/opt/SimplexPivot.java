// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

public interface SimplexPivot {
  /** nonbasic gradient method, or "steepest decent policy"
   * 
   * <p>pivot designed by G. B. Dantzig that works decent for most practical problems
   * but performs poorly on the Klee-Minty cube */
  static final SimplexPivot NONBASIC_GRADIENT = new SimplexPivot() {
    @Override
    public int get(Tensor tab, int j, int n) {
      Integer pivot = null;
      RealScalar min = null;
      int m = tab.length() - 1;
      for (int i = 0; i < m; ++i) {
        RealScalar tab_ij = (RealScalar) tab.Get(i, j);
        if (tab_ij.signInt() == 1) {
          RealScalar ratio = (RealScalar) tab.Get(i, n).divide(tab_ij);
          if (min == null || 0 < min.compareTo(ratio)) {
            min = ratio;
            pivot = i;
          }
        }
      }
      return pivot;
    }
  };
  /** first viable index
   * for experimentation only, does not work on all problems yet */
  static final SimplexPivot FIRST = new SimplexPivot() {
    @Override
    public int get(Tensor tab, int j, int n) {
      int m = tab.length() - 1;
      for (int i = 0; i < m; ++i) {
        RealScalar tab_ij = (RealScalar) tab.Get(i, j);
        if (tab_ij.signInt() == 1)
          return i;
      }
      throw new RuntimeException();
    }
  };
  /** p.50 greatest increment method NOT YET IMPLEMENTED */

  /** p.50 all variable gradient method NOT YET IMPLEMENTED */
  // ---
  /** @param tab simplex tableau with n + 1 columns
   * @param j < n
   * @param n
   * @return */
  int get(Tensor tab, int j, int n);
}
