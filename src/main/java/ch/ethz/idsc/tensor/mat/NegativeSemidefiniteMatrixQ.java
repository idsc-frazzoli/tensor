// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeSemidefiniteMatrixQ.html">NegativeSemidefiniteMatrixQ</a> */
public enum NegativeSemidefiniteMatrixQ {
  ;
  /** @param matrix hermitian
   * @return true if matrix is negative semi-definite
   * @throws TensorRuntimeException if input is not a hermitian matrix */
  public static boolean ofHermitian(Tensor matrix) {
    return !CholeskyDecomposition.of(matrix).diagonal().flatten(0) //
        .map(SignInterface.class::cast) //
        .filter(signInterface -> signInterface.signInt() > 0) // Scalars.lessThan(RealScalar.ZERO, scalar)
        .findAny().isPresent();
  }
}
