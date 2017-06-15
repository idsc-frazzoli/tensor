// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PositiveSemidefiniteMatrixQ.html">PositiveSemidefiniteMatrixQ</a> */
public enum PositiveSemidefiniteMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix and the matrix is positive semi-definite
   * @throws TensorRuntimeException if result cannot be established */
  public static boolean ofHermitian(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        !CholeskyDecomposition.of(tensor).diagonal().flatten(0) //
            .map(SignInterface.class::cast) //
            .filter(signInterface -> signInterface.signInt() < 0) // Scalars.lessThan(scalar, RealScalar.ZERO)) //
            .findAny().isPresent();
  }
}
