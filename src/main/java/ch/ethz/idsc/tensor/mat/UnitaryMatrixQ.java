// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitaryMatrixQ.html">UnitaryMatrixQ</a> */
public enum UnitaryMatrixQ {
  ;
  /** Mathematica definition:
   * "A matrix m is unitary if m.ConjugateTranspose[m] is the identity matrix."
   * 
   * @param matrix
   * @return true, if matrix.ConjugateTranspose[matrix] is the identity matrix */
  public static boolean of(Tensor matrix) {
    return Chop.isZeros(matrix.dot(ConjugateTranspose.of(matrix)).subtract(IdentityMatrix.of(matrix.length())));
  }
}
