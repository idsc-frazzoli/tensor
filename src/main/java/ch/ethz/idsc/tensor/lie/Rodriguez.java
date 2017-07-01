// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Sinc;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** Rodriguez formula is a fast and robust method to
 * compute the exponential of a skew symmetric 3x3 matrix.
 * 
 * formula taken from Blanes/Casas
 * "A concise introduction to geometric numerical integration"
 * p. 131 */
public enum Rodriguez {
  ;
  private static final Tensor ID3 = IdentityMatrix.of(3);
  private static final Scalar HALF = RationalScalar.of(1, 2);

  /** @param vector of length() == 3
   * @return MatrixExp[Cross[vector]] */
  public static Tensor of(Tensor vector) {
    Scalar beta = Norm._2.of(vector);
    Scalar s1 = Sinc.FUNCTION.apply(beta);
    Tensor X1 = Cross.of(vector.multiply(s1));
    Scalar h2 = Sinc.FUNCTION.apply(beta.multiply(HALF));
    Scalar r2 = Sqrt.FUNCTION.apply(h2.multiply(h2).multiply(HALF));
    Tensor X2 = Cross.of(vector.multiply(r2));
    return ID3.add(X1).add(X2.dot(X2));
  }
}
