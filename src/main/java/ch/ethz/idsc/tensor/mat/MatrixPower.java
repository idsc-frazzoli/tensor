// code by jph
package ch.ethz.idsc.tensor.mat;

import java.math.BigInteger;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.BinaryPower;

/** Implementation is consistent with Mathematica.
 * 
 * For non-square matrix input:
 * <pre>
 * MatrixPower[{{1, 2}}, 0] => Exception
 * MatrixPower[{{1, 2}}, 1] => Exception
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPower.html">MatrixPower</a> */
public class MatrixPower extends BinaryPower<Tensor> {
  /** @param matrix square
   * @param exponent
   * @return matrix ^ exponent
   * @throws Exception if matrix is not square */
  public static Tensor of(Tensor matrix, long exponent) {
    return of(matrix, BigInteger.valueOf(exponent));
  }

  /** @param matrix square
   * @param exponent
   * @return matrix ^ exponent
   * @throws Exception if matrix is not square */
  public static Tensor of(Tensor matrix, BigInteger exponent) {
    return new MatrixPower(matrix.length()) //
        .apply(SquareMatrixQ.require(matrix), exponent);
  }

  // ---
  private final int n;

  private MatrixPower(int n) {
    this.n = n;
  }

  @Override // from BinaryPower
  public Tensor zeroth() {
    return IdentityMatrix.of(n);
  }

  @Override // from BinaryPower
  public Tensor invert(Tensor matrix) {
    return Inverse.of(matrix);
  }

  @Override // from BinaryPower
  public Tensor multiply(Tensor matrix1, Tensor matrix2) {
    return matrix1.dot(matrix2);
  }
}
