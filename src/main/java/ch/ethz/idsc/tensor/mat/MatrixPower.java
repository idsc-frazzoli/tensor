// code by jph
package ch.ethz.idsc.tensor.mat;

import java.math.BigInteger;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.BinaryPower;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPower.html">MatrixPower</a> */
public class MatrixPower extends BinaryPower<Tensor> {
  /** @param m square matrix
   * @param exponent
   * @return m ^ exponent */
  public static Tensor of(Tensor m, long exponent) {
    return of(m, BigInteger.valueOf(exponent));
  }

  public static Tensor of(Tensor m, BigInteger exponent) {
    return new MatrixPower(m.length()).apply(m, exponent);
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
