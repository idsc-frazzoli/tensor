// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixExp.html">MatrixExp</a> */
public class MatrixExp {
  public static final int MAXITER = 100;
  public static final double TENSOR_EPS = 1E-40;

  /** @param m square matrix
   * @return exp(m) = I + m + m^2/2 + m^3/6 + ... */
  public static Tensor of(Tensor m) {
    final int n = m.length();
    Tensor sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n);
    for (int k = 1; k < MAXITER && TENSOR_EPS < Norm._1.of(nxt).number().doubleValue(); ++k) {
      nxt = nxt.dot(m).multiply(RationalScalar.of(1, k));
      sum = sum.add(nxt);
    }
    if (TENSOR_EPS < Norm._1.of(nxt).number().doubleValue())
      throw new IllegalArgumentException();
    return sum;
  }
}
