// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixExp.html">MatrixExp</a> */
public interface MatrixExp {
  /** @param m square matrix
   * @return exp(m) = I + m + m^2/2 + m^3/6 + ... */
  public static Tensor of(Tensor m) {
    return new MatrixExpImpl(m).sum;
  }
}
