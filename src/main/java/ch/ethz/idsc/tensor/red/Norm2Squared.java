// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.AbsSquared;

/** the square of Norm._2 is not a norm therefore the class does not
 * implement {@link NormInterface}.
 * 
 * @see Norm
 * @see AbsSquared */
public enum Norm2Squared {
  ;
  /** @param vector
   * @return squared euclidean norm of given vector, i.e. || v1 || ^ 2
   * @throws Exception if vector is empty */
  public static Scalar ofVector(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .map(AbsSquared.FUNCTION) //
        .reduce(Scalar::add).get();
  }

  /** @param v1 vector
   * @param v2 vector
   * @return squared euclidean norm of vector difference, i.e. || v1 - v2 || ^ 2 */
  public static Scalar between(Tensor v1, Tensor v2) {
    return ofVector(v1.subtract(v2));
  }

  /** @param matrix
   * @return 2-norm of matrix squared */
  public static Scalar ofMatrix(Tensor matrix) {
    Scalar value = Norm._2.ofMatrix(matrix);
    return value.multiply(value);
  }
}
