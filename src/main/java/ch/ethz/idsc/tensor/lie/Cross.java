// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** implementation consistent with Mathematica:
 * 
 * <pre>
 * Cross[{x, y}] == {-y, x}
 * Cross[{x, y, z}] throws an exception
 * Cross[{px, py, pz}, {qx, qy, qz}] cross product p x q between p and q
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cross.html">Cross</a> */
public enum Cross {
  ;
  /** Cross[{x, y}] == {-y, x}
   * 
   * Cross[{x, y}] == RotationMatrix.of[90 degree] . {x, y}
   * 
   * @param vector with two entries
   * @return given vector rotated by 90[deg] counter-clockwise
   * @throws Exception input is not a vector of length 2 */
  public static Tensor of(Tensor vector) {
    if (vector.length() == 2)
      return Tensors.of(vector.Get(1).negate(), vector.Get(0));
    throw TensorRuntimeException.of(vector);
  }

  /** @param a vector with 3 entries
   * @param b vector with 3 entries
   * @return cross product a x b
   * @throws Exception if a or b is not a vector of length 3 */
  public static Tensor of(Tensor a, Tensor b) {
    if (a.length() == 3 && b.length() == 3) {
      // the simple implementation is:
      // return of(a).dot(b);
      //
      // however, we carry out the steps explicitly for speed:
      Scalar a0 = a.Get(0);
      Scalar a1 = a.Get(1);
      Scalar a2 = a.Get(2);
      Scalar b0 = b.Get(0);
      Scalar b1 = b.Get(1);
      Scalar b2 = b.Get(2);
      return Tensors.of( //
          a1.multiply(b2).subtract(a2.multiply(b1)), //
          a2.multiply(b0).subtract(a0.multiply(b2)), //
          a0.multiply(b1).subtract(a1.multiply(b0)));
    }
    throw TensorRuntimeException.of(a, b);
  }

  /** gives skew matrix based on 3 vector entries
   * [ 0 -a2 a1 ]
   * [ a2 0 -a0 ]
   * [ -a1 a0 0 ]
   * 
   * @param vector with 3 entries
   * @return skew symmetric 3 x 3 matrix representing cross product mapping */
  public static Tensor skew3(Tensor vector) {
    if (vector.length() != 3)
      throw TensorRuntimeException.of(vector);
    Scalar a0 = vector.Get(0);
    Scalar a1 = vector.Get(1);
    Scalar a2 = vector.Get(2);
    return Tensors.matrix(new Scalar[][] { //
        { RealScalar.ZERO, a2.negate(), a1 }, //
        { a2, RealScalar.ZERO, a0.negate() }, //
        { a1.negate(), a0, RealScalar.ZERO } });
  }
}
