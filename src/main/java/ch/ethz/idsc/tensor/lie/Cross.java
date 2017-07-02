// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cross.html">Cross</a> */
public enum Cross {
  ;
  /** @param a vector with 3 entries
   * @param b vector with 3 entries
   * @return cross product a x b */
  public static Tensor of(Tensor a, Tensor b) {
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
