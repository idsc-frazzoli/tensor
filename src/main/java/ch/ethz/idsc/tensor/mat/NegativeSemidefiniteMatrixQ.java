// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeSemidefiniteMatrixQ.html">NegativeSemidefiniteMatrixQ</a> */
public enum NegativeSemidefiniteMatrixQ {
  ;
  /** @param matrix hermitian
   * @return true if matrix is negative semi-definite */
  public static boolean ofHermitian(Tensor matrix) {
    return !CholeskyDecomposition.of(matrix).diagonal().flatten(0) //
        .map(Scalar.class::cast) //
        .filter(scalar -> Scalars.lessThan(ZeroScalar.get(), scalar)) //
        .findAny().isPresent();
  }
}
