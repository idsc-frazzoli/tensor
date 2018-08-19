// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.OrthogonalMatrixQ;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcCos;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sinc;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** Olinde Rodrigues' formula is a fast and robust method to
 * compute the exponential of a skew symmetric 3x3 matrix.
 * 
 * formula taken from Blanes/Casas
 * "A concise introduction to geometric numerical integration"
 * p. 131
 * 
 * The formula for the logarithm is taken from a book by Chirikjian */
public enum Rodrigues {
  ;
  private static final Tensor ID3 = IdentityMatrix.of(3);
  private static final Scalar HALF = RationalScalar.HALF;
  private static final Scalar TWO = RealScalar.of(2);

  /** @param vector of length() == 3
   * @return 3x3 rotation matrix identical to MatrixExp[Cross[vector]]
   * @throws Exception if input vector length != 3 */
  public static Tensor exp(Tensor vector) {
    Scalar beta = Norm._2.ofVector(vector);
    Scalar s1 = Sinc.FUNCTION.apply(beta);
    Tensor X1 = Cross.of(vector.multiply(s1));
    Scalar h2 = Sinc.FUNCTION.apply(beta.multiply(HALF));
    Scalar r2 = Sqrt.FUNCTION.apply(h2.multiply(h2).multiply(HALF));
    Tensor X2 = Cross.of(vector.multiply(r2));
    return ID3.add(X1).add(X2.dot(X2));
  }

  /** @param matrix with dimensions 3 x 3 that satisfies OrthogonalMatrixQ
   * @return skew-symmetric 3 x 3 matrix X with exp X == matrix */
  public static Tensor logMatrix(Tensor matrix) {
    if (MatrixQ.ofSize(matrix, 3, 3) && OrthogonalMatrixQ.of(matrix)) {
      Scalar theta = theta(matrix);
      return Scalars.isZero(theta) //
          ? Array.zeros(3, 3) //
          : matrix.subtract(Transpose.of(matrix)).multiply(theta.divide(Sin.FUNCTION.apply(theta).multiply(TWO)));
    }
    throw TensorRuntimeException.of(matrix);
  }

  /** @param matrix with dimensions 3 x 3 that satisfies OrthogonalMatrixQ
   * @return vector of length 3 */
  public static Tensor log(Tensor matrix) {
    Tensor log = logMatrix(matrix);
    return Tensors.of(log.Get(2, 1), log.Get(0, 2), log.Get(1, 0));
  }

  private static Scalar theta(Tensor matrix) {
    Scalar value = matrix.Get(0, 0).add(matrix.Get(1, 1)).add(matrix.Get(2, 2)) //
        .subtract(RealScalar.ONE).divide(TWO);
    return ArcCos.FUNCTION.apply(value);
  }
}
