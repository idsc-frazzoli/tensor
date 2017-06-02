// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PositiveSemidefiniteMatrixQ.html">PositiveSemidefiniteMatrixQ</a> */
public enum PositiveSemidefiniteMatrixQ {
  ;
  /** @param matrix hermitian
   * @return true if matrix is positive semi-definite
   * @throws TensorRuntimeException if input is not a hermitian matrix */
  public static boolean ofHermitian(Tensor matrix) {
    return !CholeskyDecomposition.of(matrix).diagonal().flatten(0) //
        .map(Scalar.class::cast) //
        .filter(scalar -> Scalars.lessThan(scalar, RealScalar.ZERO)) //
        .findAny().isPresent();
  }
}
