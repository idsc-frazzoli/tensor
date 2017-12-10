// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Projection.html">Projection</a> */
public class Projection implements TensorUnaryOperator {
  /** function is equivalent to Mathematica::Projection
   * 
   * <p>however, for repeated projection onto the same vector v, use
   * {@link Projection#on(Tensor)}
   * 
   * @param u
   * @param v
   * @return */
  public static Tensor of(Tensor u, Tensor v) {
    return on(v).apply(u);
  }

  /** @param v
   * @return */
  public static Projection on(Tensor v) {
    return new Projection(v);
  }

  // ---
  private final Tensor vc;
  private final Tensor vs;

  private Projection(Tensor v) {
    vc = Conjugate.of(v);
    Scalar scalar = vc.dot(v).Get();
    if (Scalars.isZero(scalar))
      throw TensorRuntimeException.of(v);
    vs = v.divide(scalar);
  }

  @Override
  public Tensor apply(Tensor u) {
    return vs.multiply(vc.dot(u).Get());
  }
}
