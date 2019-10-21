// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

/** selects entry with largest absolute value
 * 
 * in order to compute the inverse of matrices with mixed unit, for instance:
 * {{1[m^2], 6[m*rad]}, {6[m*rad], 16[rad^2]}}
 * the pivot is computer over the absolute numeric value of the columns */
/* package */ enum PivotArgMaxAbs implements Pivot {
  INSTANCE;
  // ---
  @Override // from Pivot
  public int get(int row, int col, int[] ind, Tensor[] lhs) {
    Scalar max = value(lhs[ind[row]].Get(col).abs());
    int arg = row;
    for (int i = row + 1; i < ind.length; ++i) {
      Scalar cmp = value(lhs[ind[i]].Get(col).abs());
      if (Scalars.lessThan(max, cmp)) {
        max = cmp;
        arg = i;
      }
    }
    return arg;
  }

  private static Scalar value(Scalar scalar) {
    return scalar instanceof Quantity //
        ? ((Quantity) scalar).value()
        : scalar;
  }
}
