// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;

/** matrix exponential via power series */
/* package */ class MatrixExpSeries {
  private static final int MAX_ITERATIONS = 500;

  /** @param matrix
   * @return */
  static Tensor of(Tensor matrix) {
    final int n = matrix.length();
    Tensor sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n);
    for (int k = 1; k <= n; ++k) {
      nxt = nxt.dot(matrix).divide(RealScalar.of(k));
      sum = sum.add(nxt);
      if (Chop.NONE.allZero(nxt))
        return sum;
    }
    sum = N.DOUBLE.of(sum); // switch to numeric precision
    for (int k = n + 1; k < MAX_ITERATIONS; ++k) {
      nxt = nxt.dot(matrix).divide(RealScalar.of(k));
      Tensor prv = sum;
      sum = sum.add(nxt);
      if (Chop.NONE.close(sum, prv))
        return sum;
    }
    throw TensorRuntimeException.of(matrix); // insufficient convergence
  }
}
