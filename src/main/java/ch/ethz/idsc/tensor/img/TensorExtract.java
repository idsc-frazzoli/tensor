// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.TensorRank;

/* package */ class TensorExtract {
  /** @param tensor not a scalar
   * @param radius non-negative
   * @param function
   * @return
   * @throws Exception if given radius is negative */
  public static Tensor of(Tensor tensor, int radius, Function<Tensor, ? extends Tensor> function) {
    ScalarQ.thenThrow(tensor);
    Integers.requirePositiveOrZero(radius);
    int rank = TensorRank.of(tensor);
    UnaryOperator<Tensor> unaryOperator = value -> convolve(value, radius, function);
    for (int level = 0; level < rank; ++level)
      tensor = TensorMap.of(unaryOperator, tensor, level);
    return tensor;
  }

  private static Tensor convolve(Tensor tensor, int radius, Function<Tensor, ? extends Tensor> function) {
    TensorExtract tensorExtract = new TensorExtract(tensor, radius);
    return Tensor.of(IntStream.range(0, tensor.length()).mapToObj(tensorExtract::extract).map(function));
  }

  // ---
  private final Tensor tensor;
  private final int radius;
  private final int length;

  private TensorExtract(Tensor tensor, int radius) {
    this.tensor = tensor;
    this.radius = radius;
    length = tensor.length();
  }

  private Tensor extract(int index) {
    return tensor.extract(Math.max(0, index - radius), Math.min(length, index + radius + 1));
  }
}
