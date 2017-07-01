// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;

/** for rotation matrices in 3D see {@link Rodriguez}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/RotationMatrix.html">RotationMatrix</a> */
public enum RotationMatrix {
  ;
  /** @param theta
   * @return 2x2 real valued orthogonal matrix that encodes the rotation by angle theta
   * [ cos -sin]
   * [ sin +cos] */
  public static Tensor of(Scalar theta) {
    Scalar cos = Cos.of(theta);
    Scalar sin = Sin.of(theta);
    return Tensors.of( //
        Tensors.of(cos, sin.negate()), //
        Tensors.of(sin, cos));
  }
}
