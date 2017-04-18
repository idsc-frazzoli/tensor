// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** the most important algorithm of all time */
/* package */ class GaussianElimination {
  final Tensor lhs;
  final int[] ind;
  final Tensor rhs;
  int transpositions = 0;

  GaussianElimination(Tensor m, Tensor b, Pivot pivot) {
    lhs = m.copy();
    int n = lhs.length();
    ind = new int[n];
    IntStream.range(0, n).forEach(index -> ind[index] = index);
    rhs = b.copy();
    for (int c0 = 0; c0 < n; ++c0) {
      int k = pivot.get(c0, ind, lhs);
      if (ind[k] != ind[c0]) {
        ++transpositions;
        int swap = ind[k];
        ind[k] = ind[c0];
        ind[c0] = swap;
      }
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
  }

  /** @return determinant */
  Scalar det() {
    Scalar scalar = IntStream.range(0, lhs.length()).boxed() //
        .map(c0 -> lhs.Get(ind[c0], c0)) //
        .reduce(Scalar::multiply) //
        .orElse(ZeroScalar.get());
    return transpositions % 2 == 0 ? scalar : scalar.negate();
  }

  /** @return x with m.dot(x) == b */
  Tensor solution() {
    Tensor sol = rhs.map(scalar -> ZeroScalar.get()); // all-zeros copy of rhs
    for (int c0 = ind.length - 1; 0 <= c0; --c0) {
      Scalar fac = lhs.Get(ind[c0], c0).invert();
      sol.set(rhs.get(ind[c0]).subtract(lhs.get(ind[c0]).dot(sol)).multiply(fac), c0);
    }
    return sol;
  }
}
