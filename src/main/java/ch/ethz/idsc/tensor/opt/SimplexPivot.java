// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;
import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Sign;

public interface SimplexPivot extends Serializable {
  /** nonbasic gradient method, or "steepest decent policy"
   * 
   * <p>pivot designed by G. B. Dantzig that works decent for most practical problems
   * but performs poorly on the Klee-Minty cube */
  static final SimplexPivot NONBASIC_GRADIENT = new SimplexPivot() {
    @Override // from SimplexPivot
    public int get(Tensor tab, int j, int n) {
      Integer pivot = null;
      Scalar min = null;
      int m = tab.length() - 1;
      for (int i = 0; i < m; ++i) {
        Scalar tab_ij = tab.Get(i, j);
        if (Sign.isPositive(tab_ij)) {
          Scalar ratio = tab.Get(i, n).divide(tab_ij);
          if (Objects.isNull(min) || 0 < Scalars.compare(min, ratio)) {
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
    @Override // from SimplexPivot
    public int get(Tensor tab, int j, int n) {
      int m = tab.length() - 1;
      for (int i = 0; i < m; ++i)
        if (Sign.isPositive(tab.Get(i, j)))
          return i;
      throw TensorRuntimeException.of(tab);
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
