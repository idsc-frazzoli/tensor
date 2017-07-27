// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeDefiniteMatrixQ.html">NegativeDefiniteMatrixQ</a> */
public enum NegativeDefiniteMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix and matrix is negative definite
   * @throws TensorRuntimeException if result cannot be established */
  public static boolean ofHermitian(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        !CholeskyDecomposition.of(tensor).diagonal().flatten(0) //
            .map(SignInterface.class::cast) //
            .anyMatch(signInterface -> signInterface.signInt() >= 0);
  }
}
