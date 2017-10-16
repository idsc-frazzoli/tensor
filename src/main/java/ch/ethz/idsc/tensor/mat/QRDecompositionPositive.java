// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** decomposition Q.R = A with R = I and Q == A if A is orthogonal */
/* package */ class QRDecompositionPositive extends QRDecompositionDefault {
  /** @param A
   * @throws Exception if input is not a matrix */
  QRDecompositionPositive(Tensor A) {
    super(A);
  }

  @Override
  Scalar sign(Scalar xk) {
    return RealScalar.ONE.negate();
  }
}
