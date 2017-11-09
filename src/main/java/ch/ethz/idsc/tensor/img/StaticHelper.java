// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum StaticHelper {
  ;
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /* package */ static Tensor transparent() {
    return TRANSPARENT.copy();
  }

  /** @param tensor
   * @param radius
   * @param unaryOperator
   * @return */
  static Tensor filter(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    if (radius < 0)
      throw new IllegalArgumentException("" + radius);
    return Tensor.of(IntStream.range(0, tensor.length()) //
        .mapToObj(index -> tensor.extract(Math.max(0, index - radius), Math.min(tensor.length(), index + radius + 1))) //
        .map(unaryOperator));
  }
}
