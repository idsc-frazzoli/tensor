// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;

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
/* package */ class GaussianElimination {
  private final Tensor lhs;
  private final int[] ind;
  private final Tensor rhs;
  private int transpositions = 0;

  /** @param matrix square and invertible
   * @param b tensor with first dimension identical to size of matrix
   * @param pivot
   * @throws TensorRuntimeException if matrix m is singular */
  GaussianElimination(Tensor matrix, Tensor b, Pivot pivot) {
    lhs = matrix.copy();
    int n = lhs.length();
    ind = IntStream.range(0, n).toArray();
    rhs = b.copy();
    for (int c0 = 0; c0 < n; ++c0) {
      swap(pivot.get(c0, c0, ind, lhs), c0);
      Scalar piv = lhs.Get(ind[c0], c0);
      if (Scalars.isZero(piv))
        throw TensorRuntimeException.of(matrix, piv);
      eliminate(c0, piv);
    }
  }

  private void eliminate(int c0, Scalar piv) {
    IntStream.range(c0 + 1, lhs.length()).forEach(c1 -> { // deliberately without parallel
      Scalar fac = lhs.Get(ind[c1], c0).divide(piv).negate();
      lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
      rhs.set(rhs.get(ind[c1]).add(rhs.get(ind[c0]).multiply(fac)), ind[c1]);
    });
  }

  /** constructs reduced row echelon form (also called row canonical form)
   * 
   * @param matrix
   * @param pivot */
  GaussianElimination(Tensor matrix, Pivot pivot) {
    lhs = matrix.copy();
    int n = lhs.length();
    int m = Unprotect.dimension1(lhs);
    ind = new int[n];
    rhs = null;
    IntStream.range(0, n).forEach(index -> ind[index] = index);
    int j = 0;
    for (int c0 = 0; c0 < n && j < m; ++j) {
      swap(pivot.get(c0, j, ind, lhs), c0);
      Scalar piv = lhs.Get(ind[c0], j);
      if (Scalars.nonZero(piv)) {
        for (int c1 = 0; c1 < n; ++c1)
          if (c1 != c0) {
            Scalar fac = lhs.Get(ind[c1], j).divide(piv).negate();
            lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
          }
        lhs.set(lhs.get(ind[c0]).divide(piv), ind[c0]);
        ++c0;
      }
    }
  }

  private void swap(int k, int c0) {
    if (k != c0) {
      ++transpositions;
      int swap = ind[k];
      ind[k] = ind[c0];
      ind[c0] = swap;
    }
  }

  /** @return lhs */
  Tensor lhs() {
    return Tensor.of(IntStream.of(ind).mapToObj(lhs::get));
  }

  /** @return determinant */
  Scalar det() {
    Scalar scalar = IntStream.range(0, lhs.length()) //
        .mapToObj(c0 -> lhs.Get(ind[c0], c0)) //
        .reduce(Scalar::multiply) //
        .get();
    return transpositions % 2 == 0 ? scalar : scalar.negate();
  }

  /** @return x with m.dot(x) == b */
  Tensor solution() {
    Tensor sol = rhs.map(Scalar::zero); // all-zeros copy of rhs
    for (int c0 = ind.length - 1; 0 <= c0; --c0) {
      Scalar factor = lhs.Get(ind[c0], c0);
      sol.set(rhs.get(ind[c0]).subtract(lhs.get(ind[c0]).dot(sol)).divide(factor), c0);
    }
    return sol;
  }
}
