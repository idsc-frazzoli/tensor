// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cross.html">Cross</a> */
public enum Cross {
  ;
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
   * [ 0 -a3 a2 ]
   * [ a3 0 -a1 ]
   * [ -a2 a1 0 ]
   * 
   * @param a vector with 3 entries
   * @return skew symmetric 3 x 3 matrix representing cross product mapping */
  public static Tensor of(Tensor a) {
    return LieAlgebras.so3().dot(a);
  }
}
