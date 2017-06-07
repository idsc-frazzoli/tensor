// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;

/** matrix exponential via power series */
/* package */ class SeriesMatrixExp {
  static final int MAXITER = 100;
  static final double TENSOR_EPS = 1E-40;
  Tensor sum;

  SeriesMatrixExp(Tensor m) {
    final int n = m.length();
    sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n);
    for (int k = 1; k < MAXITER && TENSOR_EPS < Norm._1.of(nxt).number().doubleValue(); ++k) {
      nxt = nxt.dot(m).multiply(RationalScalar.of(1, k));
      sum = sum.add(nxt);
    }
    if (TENSOR_EPS < Norm._1.of(nxt).number().doubleValue())
      throw new IllegalArgumentException();
  }
}
