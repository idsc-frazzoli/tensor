// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/* package */ class TensorExtract extends AbstractExtract {
  static Tensor convolve(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    TensorExtract extract = new TensorExtract(tensor, radius);
    return Tensor.of(IntStream.range(0, tensor.length()).mapToObj(extract::extract).map(unaryOperator));
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
