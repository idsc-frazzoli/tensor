// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ImageFilter.html">ImageFilter</a> */
public class ImageFilter extends AbstractExtract {
  public static Tensor of(Tensor tensor, int radius, UnaryOperator<Tensor> unaryOperator) {
    ImageFilter blockExtract = new ImageFilter(tensor, StaticHelper.requirePositiveOrZero(radius));
    return Array.of(index -> unaryOperator.apply(blockExtract.block(index)), Dimensions.of(tensor));
  }

  // ---
  private final List<Integer> dimensions;

  private ImageFilter(Tensor tensor, int radius) {
    super(tensor, radius);
    dimensions = Dimensions.of(tensor);
  }

  private Tensor block(List<Integer> index) {
    int size = index.size();
    List<Integer> ofs = new ArrayList<>(size);
    List<Integer> len = new ArrayList<>(size);
    for (int count = 0; count < size; ++count) {
      int start = Math.max(0, index.get(count) - radius);
      ofs.add(start);
      len.add(Math.min(dimensions.get(count), index.get(count) + radius + 1) - start);
    }
    return tensor.block(ofs, len);
  }
}
