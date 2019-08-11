// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Gaussian elimination is the most important algorithm of all time.
 * 
 * <p>Quote from Wikipedia:
 * The method of Gaussian elimination appears in the Chinese mathematical text Chapter
 * Eight Rectangular Arrays of The Nine Chapters on the Mathematical Art. Its use is
 * illustrated in eighteen problems, with two to five equations. The first reference to
 * the book by this title is dated to 179 CE, but parts of it were written as early as
 * approximately 150 BCE. It was commented on by Liu Hui in the 3rd century.
 * The method in Europe stems from the notes of Isaac Newton. In 1670, he wrote that all
 * the algebra books known to him lacked a lesson for solving simultaneous equations,
 * which Newton then supplied. Cambridge University eventually published the notes as
 * Arithmetica Universalis in 1707 long after Newton left academic life. The notes were
 * widely imitated, which made (what is now called) Gaussian elimination a standard lesson
 * in algebra textbooks by the end of the 18th century. Carl Friedrich Gauss in 1810
 * devised a notation for symmetric elimination that was adopted in the 19th century by
 * professional hand computers to solve the normal equations of least-squares problems.
 * The algorithm that is taught in high school was named for Gauss only in the 1950s as
 * a result of confusion over the history of the subject. */
/* package */ class GaussianElimination extends AbstractReduce {
  /** @param matrix square and invertible
   * @param b tensor with first dimension identical to size of matrix
   * @param pivot
   * @throws TensorRuntimeException if matrix m is singular */
  public static Tensor of(Tensor matrix, Pivot pivot, Tensor b) {
    return new GaussianElimination(matrix, pivot, b).solve();
  }

  // ---
  private final Tensor rhs;

  private GaussianElimination(Tensor matrix, Pivot pivot, Tensor b) {
    super(matrix, pivot);
    rhs = b.copy();
    for (int c0 = 0; c0 < lhs.length; ++c0) {
      swap(pivot.get(c0, c0, ind, lhs), c0);
      Scalar piv = lhs[ind[c0]].Get(c0);
      if (Scalars.isZero(piv))
        throw TensorRuntimeException.of(matrix, piv);
      eliminate(c0, piv);
    }
  }

  private void eliminate(int c0, Scalar piv) {
    int ic0 = ind[c0];
    for (int c1 = c0 + 1; c1 < lhs.length; ++c1) { // deliberately without parallel
      int ic1 = ind[c1];
      Scalar fac = lhs[ic1].Get(c0).divide(piv).negate();
      lhs[ic1] = lhs[ic1].add(lhs[ic0].multiply(fac));
      rhs.set(rhs.get(ic1).add(rhs.get(ic0).multiply(fac)), ic1);
    }
  }

  /** @return x with m.dot(x) == b */
  private Tensor solve() {
    Tensor sol = rhs.map(Scalar::zero); // all-zeros copy of rhs
    for (int c0 = ind.length - 1; 0 <= c0; --c0) {
      int ic0 = ind[c0];
      Scalar factor = lhs[ic0].Get(c0);
      sol.set(rhs.get(ic0).subtract(lhs[ic0].dot(sol)).divide(factor), c0);
    }
    return sol;
  }
}
