// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Orthogonalize.html">Orthogonalize</a> */
public enum Orthogonalize {
  ;
  /** @param matrix
   * @return */
  public static Tensor of(Tensor matrix) {
    // TODO this is preliminary and does not work as in Mathematica yet!
    QRDecomposition qrDecomposition = QRDecomposition.of(matrix);
    return qrDecomposition.getQ();
  }
}
