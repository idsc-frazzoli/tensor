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
  /** @param vector
   * @return projection operator to given vector */
  public static TensorUnaryOperator on(Tensor vector) {
    return new Projection(vector);
  }

  /** Hint: function is not commutative!
   * 
   * @param u
   * @param v
   * @return Mathematica::Projection[u, v] */
  public static Tensor of(Tensor u, Tensor v) {
    return on(v).apply(u);
  }

  // ---
  private final Tensor vc;
  private final Tensor vs;

  private Projection(Tensor vector) {
    vc = Conjugate.of(vector);
    Scalar scalar = vc.dot(vector).Get();
    if (Scalars.isZero(scalar))
      throw TensorRuntimeException.of(vector);
    vs = vector.divide(scalar);
  }

  @Override // from TensorUnaryOperator
  public Tensor apply(Tensor u) {
    return vs.multiply(vc.dot(u).Get());
  }
}
