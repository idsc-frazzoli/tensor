// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** Important: not all algorithms are tested for {@link Quaternion} input.
 * The consistent handling of the non-commutativity of the multiplication
 * may require significant modifications of the existing algorithms.
 * 
 * <p>Mathematica does not serve as a role model for quaternions. Their
 * corresponding functionality appears cumbersome and limited.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quaternion.html">Quaternion</a> */
public interface Quaternion extends Scalar, ConjugateInterface, SqrtInterface {
  static final Quaternion ZERO = of(0, 0, 0, 0);
  static final Quaternion ONE = of(1, 0, 0, 0);

  /** @param w
   * @param xyz vector of length 3
   * @return
   * @throws Exception if given xyz is not a vector of length 3 */
  static Quaternion of(Scalar w, Tensor xyz) {
    return new QuaternionImpl(Objects.requireNonNull(w), VectorQ.requireLength(xyz, 3).copy());
  }

  /** @param w
   * @param x
   * @param y
   * @param z
   * @return */
  static Quaternion of(Scalar w, Scalar x, Scalar y, Scalar z) {
    return new QuaternionImpl(Objects.requireNonNull(w), Tensors.of(x, y, z));
  }

  /** @param w
   * @param x
   * @param y
   * @param z
   * @return */
  static Quaternion of(Number w, Number x, Number y, Number z) {
    return new QuaternionImpl(RealScalar.of(w), Tensors.vector(x, y, z));
  }

  @Override // from Scalar
  Quaternion multiply(Scalar scalar);

  @Override // from Scalar
  Quaternion negate();

  @Override // from Scalar
  Quaternion divide(Scalar scalar);

  @Override // from Scalar
  Quaternion reciprocal();

  @Override // from ConjugateInterface
  Quaternion conjugate();

  @Override // from SqrtInterface
  Quaternion sqrt();

  /** @return real part */
  Scalar w();

  /** @return unmodifiable vector of length 3 with coefficients {x, y, z} */
  Tensor xyz();
}
