// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LinearSolve.html">LinearSolve</a> */
public enum LinearSolve {
  ;
  private static interface Pivot {
    /** @param c0 row and column offset
     * @param ind permutation
     * @param lhs matrix
     * @return row index between c0 and ind.length that should be used as pivot element */
    int get(int c0, int[] ind, Tensor lhs);
  }

  private static final Pivot maxAbs = new Pivot() {
    @Override
    public int get(int c0, int[] ind, Tensor lhs) {
      int k = c0;
      double max = 0;
      for (int c1 = c0; c1 < ind.length; ++c1) {
        double abs = lhs.Get(ind[c1], c0).abs().number().doubleValue();
        if (max < abs) {
          max = abs;
          k = c1;
        }
      }
      return k;
    }
  };
  /** picks the first non Zero element in the column as pivot */
  private static final Pivot firstNonZero = new Pivot() {
    @Override
    public int get(int c0, int[] ind, Tensor lhs) {
      for (int c1 = c0; c1 < ind.length; ++c1)
        if (!lhs.Get(ind[c1], c0).equals(ZeroScalar.get()))
          return c1;
      return c0;
    }
  };

  /** @param m
   * @param b
   * @return x with m.x == b or null if fails */
  public static Tensor of(Tensor m, Tensor b) {
    return gaussianElimination(m, b, maxAbs);
  }

  /** doesn't require Scalar::abs
   * 
   * @param m
   * @param b
   * @return x with m.x == b or null if fails */
  public static Tensor withoutAbs(Tensor m, Tensor b) { // function name is not final
    return gaussianElimination(m, b, firstNonZero);
  }

  /** Gaussian elimination
   * implementation not efficient by concise
   * 
   * @param m square matrix
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.x == b */
  private static Tensor gaussianElimination(Tensor m, Tensor b, Pivot pivot) {
    if (!Dimensions.isArray(m))
      throw new IllegalArgumentException();
    Tensor lhs = m.copy();
    final int n = lhs.length();
    final int[] ind = new int[n];
    for (int index = 0; index < n; ++index)
      ind[index] = index;
    Tensor rhs = b.copy();
    for (int c0 = 0; c0 < n; ++c0) {
      int k = pivot.get(c0, ind, lhs);
      int swap = ind[k];
      ind[k] = ind[c0];
      ind[c0] = swap;
      final Scalar piv = lhs.Get(ind[c0], c0);
      if (piv.equals(ZeroScalar.get())) {
        // TODO treat case if, because there is still hope
        throw new IllegalArgumentException("matrix singular");
      }
      final Scalar den = piv.invert();
      for (int c1 = c0 + 1; c1 < n; ++c1) {
        Scalar fac = (Scalar) lhs.get(ind[c1], c0).multiply(den).negate();
        lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
        rhs.set(rhs.get(ind[c1]).add(rhs.get(ind[c0]).multiply(fac)), ind[c1]);
      }
    }
    Tensor sol = rhs.map(scalar -> ZeroScalar.get()); // all-zeros copy of rhs
    for (int c0 = n - 1; 0 <= c0; --c0) {
      Scalar fac = lhs.Get(ind[c0], c0).invert();
      sol.set(rhs.get(ind[c0]).subtract(lhs.get(ind[c0]).dot(sol)).multiply(fac), c0);
    }
    return sol;
  }
}
