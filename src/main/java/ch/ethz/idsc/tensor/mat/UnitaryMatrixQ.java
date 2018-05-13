// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitaryMatrixQ.html">UnitaryMatrixQ</a> */
public enum UnitaryMatrixQ {
  ;
  /** Mathematica definition:
   * "A matrix m is unitary if m.ConjugateTranspose[m] is the identity matrix."
   * 
   * @param tensor
   * @return true, if tensor is a matrix and tensor.ConjugateTranspose[tensor] is the identity matrix */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        NullSpace.CHOP_DEFAULT.close(tensor.dot(ConjugateTranspose.of(tensor)), IdentityMatrix.of(tensor.length()));
  }
}
