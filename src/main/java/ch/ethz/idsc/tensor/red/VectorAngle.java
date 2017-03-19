// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ArcCos;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorAngle.html">VectorAngle</a> */
public class VectorAngle {
  public static RealScalar of(Tensor u, Tensor v) {
    return (RealScalar) ArcCos.function.apply( //
        ((Scalar) u.dot(v.conjugate())) //
            .divide(Norm._2.of(u).multiply(Norm._2.of(u))));
  }
}
