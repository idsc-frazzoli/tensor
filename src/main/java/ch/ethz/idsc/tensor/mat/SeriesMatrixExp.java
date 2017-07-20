// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;

/** matrix exponential via power series */
/* package */ class SeriesMatrixExp {
  static final int MAXITER = 100;

  /** @param m
   * @return */
  public static Tensor of(Tensor m) {
    final int n = m.length();
    Tensor sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n); // identity matrix
    for (int k = 1; k < MAXITER; ++k) {
      nxt = nxt.dot(m).multiply(RationalScalar.of(1, k));
      sum = sum.add(nxt);
      Scalar norm = Norm._1.of(nxt);
      if (Scalars.isZero(norm))
        return sum;
      if (Chop._40.allZero(N.of(norm)))
        return N.of(sum);
    }
    throw TensorRuntimeException.of(m);
  }
}
