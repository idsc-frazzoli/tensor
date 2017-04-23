// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.BinaryExponentiation;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPower.html">MatrixPower</a> */
public class MatrixPower implements BinaryExponentiation<Tensor> {
  /** @param m square matrix
   * @param exponent
   * @return m ^ exponent */
  public static Tensor of(Tensor m, long exponent) {
    return 0 <= exponent ? //
        BinaryExponentiation.positive(new MatrixPower(m), exponent) : //
        BinaryExponentiation.positive(new MatrixPower(Inverse.of(m)), -exponent);
  }

  private final Tensor matrix;

  private MatrixPower(Tensor matrix) {
    this.matrix = matrix;
  }

  @Override
  public Tensor zeroth() {
    return IdentityMatrix.of(matrix.length());
  }

  @Override
  public Tensor square(Tensor tensor) {
    return tensor.dot(tensor);
  }

  @Override
  public Tensor raise(Tensor tensor) {
    return tensor.dot(matrix);
  }
}
