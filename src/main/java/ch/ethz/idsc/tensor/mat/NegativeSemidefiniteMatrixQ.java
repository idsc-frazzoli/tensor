// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeSemidefiniteMatrixQ.html">NegativeSemidefiniteMatrixQ</a> */
public enum NegativeSemidefiniteMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix and the matrix is negative semi-definite
   * @throws TensorRuntimeException if result cannot be established */
  public static boolean ofHermitian(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        CholeskyDecomposition.of(tensor).diagonal().stream() //
            .map(Scalar.class::cast).allMatch(Sign::isNegativeOrZero);
  }
}
