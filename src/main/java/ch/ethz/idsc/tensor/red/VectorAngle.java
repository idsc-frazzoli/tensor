// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.ArcCos;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorAngle.html">VectorAngle</a> */
public enum VectorAngle {
  ;
  private static final TensorUnaryOperator NORMALIZE = Normalize.with(Norm._2);

  /** @param u
   * @param v
   * @return angle between the vectors u and v, or empty if either norm of u or v is zero */
  public static Optional<Scalar> of(Tensor u, Tensor v) {
    Scalar nu = Norm._2.ofVector(u);
    Scalar nv = Norm._2.ofVector(v);
    if (Scalars.isZero(nu) || Scalars.isZero(nv))
      return Optional.empty();
    Scalar ratio = ExactScalarQ.all(u) || ExactScalarQ.all(v) //
        ? u.dot(Conjugate.of(v)).divide(nu).divide(nv).Get()
        : NORMALIZE.apply(u).dot(NORMALIZE.apply(Conjugate.of(v))).Get();
    if (ratio instanceof RealScalar)
      // due to numerical inaccuracy, for instance, ratio == 1.0000000000000002 may occur
      ratio = Clips.absoluteOne().apply(ratio); // clip to [-1, 1]
    return Optional.of(ArcCos.FUNCTION.apply(ratio));
  }
}
