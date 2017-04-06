// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** Gaussian elimination
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/LinearSolve.html">LinearSolve</a> */
public enum LinearSolve {
  ;
  /** gives solution to linear system of equations.
   * scalar entries are required to implement
   * Comparable<Scalar> for pivoting.
   * 
   * @param m square matrix of {@link Scalar} that implement absolute value abs()
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.dot(x) == b
   * throws an exception if inversion of m fails */
  public static Tensor of(Tensor m, Tensor b) {
    return gaussianElimination(m, b, Pivot.argMaxAbs);
  }

  /** method only checks for non-zero
   * and doesn't use Scalar::abs().
   * 
   * @param m square matrix
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.dot(x) == b
   * throws an exception if inversion of m fails */
  public static Tensor withoutAbs(Tensor m, Tensor b) { // function name is not final
    return gaussianElimination(m, b, Pivot.firstNonZero);
  }

  /** @param m square matrix
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.dot(x) == b
   * throws an exception if inversion of m fails */
  private static Tensor gaussianElimination(Tensor m, Tensor b, Pivot pivot) {
    if (!Dimensions.isArray(m))
      throw TensorRuntimeException.of(m);
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
      if (piv.equals(ZeroScalar.get()))
        // TODO there might be still hope depending on rhs...?
        throw TensorRuntimeException.of(m);
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
