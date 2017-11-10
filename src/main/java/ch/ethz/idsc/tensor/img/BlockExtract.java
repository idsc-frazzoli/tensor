// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;

/* package */ class BlockExtract extends AbstractExtract {
  static Tensor convolve(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    BlockExtract extract = new BlockExtract(tensor, radius);
    return Array.of(index -> unaryOperator.apply(extract.block(index)), Dimensions.of(tensor));
  }

  // ---
  private final List<Integer> dimensions;

  private BlockExtract(Tensor tensor, int radius) {
    super(tensor, radius);
    dimensions = Dimensions.of(tensor);
  }

  private Tensor block(List<Integer> index) {
    List<Integer> ofs = new ArrayList<>();
    List<Integer> len = new ArrayList<>();
    for (int count = 0; count < index.size(); ++count) {
      int start = Math.max(0, index.get(count) - radius);
      ofs.add(start);
      len.add(Math.min(dimensions.get(count), index.get(count) + radius + 1) - start);
    }
    return tensor.block(ofs, len);
  }
}
