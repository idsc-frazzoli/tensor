// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ArcCos;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorAngle.html">VectorAngle</a> */
public enum VectorAngle {
  ;
  /** @param u
   * @param v
   * @return angle between the vectors u and v
   * @throws Exception if norm of either u or v is zero */
  public static Scalar of(Tensor u, Tensor v) {
    Scalar ratio = u.dot(Conjugate.of(v)).divide(Norm._2.ofVector(u)).divide(Norm._2.ofVector(v)).Get();
    // due to numerical inaccuracy, for instance, ratio == 1.0000000000000002 may occur
    return ArcCos.FUNCTION.apply(Clip.absoluteOne().apply(ratio)); // clip to [-1, 1]
  }
}
