// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;

/** for rotation matrices in 3D see {@link Rodrigues}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/RotationMatrix.html">RotationMatrix</a> */
public enum RotationMatrix {
  ;
  /** @param angle
   * @return 2x2 orthogonal matrix that encodes the rotation by given angle
   * [ cos -sin]
   * [ sin +cos] */
  public static Tensor of(Scalar angle) {
    Scalar cos = Cos.FUNCTION.apply(angle);
    Scalar sin = Sin.FUNCTION.apply(angle);
    return Tensors.matrix(new Scalar[][] { //
        { cos, sin.negate() }, //
        { sin, cos } });
  }
}
