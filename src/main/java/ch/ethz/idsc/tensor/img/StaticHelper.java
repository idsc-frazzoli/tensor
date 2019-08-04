// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/* package */ enum StaticHelper {
  ;
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  static Tensor transparent() {
    return TRANSPARENT.copy();
  }

  static String colorlist(String name) {
    if (name.charAt(0) == '_')
      name = name.substring(1);
    return "/colorlist/" + name.toLowerCase() + ".csv";
  }

  /***************************************************/
  /** @param tensor of dimension n x 4
   * @param alpha in the range [0, 1, ..., 255]
   * @return */
  static Tensor withAlpha(Tensor tensor, int alpha) {
    return Tensor.of(tensor.stream().map(withAlpha(alpha)));
  }

  /** @param alpha in the range [0, 1, ..., 255]
   * @return operator that maps a vector of the form rgba to rgb, alpha */
  private static TensorUnaryOperator withAlpha(int alpha) {
    Scalar scalar = RealScalar.of(alpha);
    return rgba -> rgba.extract(0, 3).append(scalar);
  }

  /***************************************************/
  /** @param tensor of dimension n x 4
   * @param opacity in the interval [0, 1]
   * @return */
  static Tensor withOpacity(Tensor tensor, Scalar opacity) {
    return Tensor.of(tensor.stream().map(withOpacity(opacity)));
  }

  /** @param opacity in the interval [0, 1]
   * @return operator that maps a vector of the form rgba to rgb, alpha*factor */
  private static TensorUnaryOperator withOpacity(Scalar opacity) {
    return rgba -> {
      Tensor copy = rgba.copy();
      copy.set(alpha -> alpha.multiply(opacity), 3);
      return copy;
    };
  }
}
