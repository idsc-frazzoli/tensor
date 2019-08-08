// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.TensorRank;

/* package */ class TensorExtract extends AbstractExtract {
  public static Tensor of(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    ScalarQ.thenThrow(tensor);
    if (radius < 0)
      throw new IllegalArgumentException("radius=" + radius);
    int rank = TensorRank.of(tensor);
    UnaryOperator<Tensor> operator = value -> convolve(value, radius, unaryOperator);
    for (int level = 0; level < rank; ++level)
      tensor = TensorMap.of(operator, tensor, level);
    return tensor;
  }

  private static Tensor convolve(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    TensorExtract tensorExtract = new TensorExtract(tensor, radius);
    return Tensor.of(IntStream.range(0, tensor.length()).mapToObj(tensorExtract::extract).map(unaryOperator));
  }

  // ---
  private final int length;

  private TensorExtract(Tensor tensor, int radius) {
    super(tensor, radius);
    length = tensor.length();
  }

  private Tensor extract(int index) {
    return tensor.extract(Math.max(0, index - radius), Math.min(length, index + radius + 1));
  }
}
