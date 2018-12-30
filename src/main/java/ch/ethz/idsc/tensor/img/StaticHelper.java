// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/* package */ enum StaticHelper {
  ;
  /* package */ static final ColorDataGradient FALLBACK = ColorDataGradient.of(Array.zeros(2, 4));
  // ---
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /* package */ static Tensor transparent() {
    return TRANSPARENT.copy();
  }

  static String colorlist(String name) {
    if (name.charAt(0) == '_')
      name = name.substring(1);
    return "/colorlist/" + name.toLowerCase() + ".csv";
  }

  /** @param tensor
   * @param alpha
   * @return */
  static Tensor withAlpha(Tensor tensor, int alpha) {
    return Tensor.of(tensor.stream().map(withAlpha(alpha)));
  }

  /** @param alpha
   * @return operator that maps a vector of the form rgba to rgb,alpha */
  private static TensorUnaryOperator withAlpha(int alpha) {
    Scalar scalar = RealScalar.of(alpha);
    return rgba -> rgba.extract(0, 3).append(scalar);
  }

  static Tensor withFactor(Tensor tensor, Scalar factor) {
    return Tensor.of(tensor.stream().map(withFactor(factor)));
  }

  /** @param factor
   * @return operator that maps a vector of the form rgba to rgb,alpha*factor */
  private static TensorUnaryOperator withFactor(Scalar factor) {
    return rgba -> {
      Tensor copy = rgba.copy();
      copy.set(alpha -> alpha.multiply(factor), 3);
      return copy;
    };
  }
}
