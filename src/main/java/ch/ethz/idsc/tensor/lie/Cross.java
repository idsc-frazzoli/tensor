// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cross.html">Cross</a> */
public enum Cross {
  ;
  /** @param a vector with 3 entries
   * @param b vector with 3 entries
   * @return cross product a x b */
  public static Tensor of(Tensor a, Tensor b) {
    return of(a).dot(b);
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
