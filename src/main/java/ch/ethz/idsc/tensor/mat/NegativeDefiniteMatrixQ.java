// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/NegativeDefiniteMatrixQ.html">NegativeDefiniteMatrixQ</a> */
public enum NegativeDefiniteMatrixQ {
  ;
  /** @param matrix hermitian
   * @return true if matrix is positive definite */
  public static boolean ofHermitian(Tensor matrix) {
    // TODO check for complex input in numeric precision
    return !CholeskyDecomposition.of(matrix).getD().flatten(0) //
        .filter(scalar -> Scalars.lessEquals(ZeroScalar.get(), (Scalar) scalar)) //
        .findAny().isPresent();
  }
}
