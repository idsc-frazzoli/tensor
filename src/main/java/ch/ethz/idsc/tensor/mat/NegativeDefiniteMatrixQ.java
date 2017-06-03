// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeDefiniteMatrixQ.html">NegativeDefiniteMatrixQ</a> */
public enum NegativeDefiniteMatrixQ {
  ;
  /** @param matrix hermitian
   * @return true if matrix is negative definite
   * @throws TensorRuntimeException if input is not a hermitian matrix */
  public static boolean ofHermitian(Tensor matrix) {
    return !CholeskyDecomposition.of(matrix).diagonal().flatten(0) //
        .map(SignInterface.class::cast) //
        .filter(signInterface -> signInterface.signInt() >= 0) // Scalars.lessEquals(RealScalar.ZERO, scalar)) //
        .findAny().isPresent();
  }
}
