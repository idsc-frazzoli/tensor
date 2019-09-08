// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** Hint: the implementation also operates on unstructured tensors.
 * This feature is in contrast to the Mathematica standard.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ImageFilter.html">ImageFilter</a> */
public class ImageFilter {
  /** @param tensor not a scalar
   * @param radius non-negative
   * @param function non-null
   * @return
   * @throws Exception if radius is negative
   * @see MedianFilter */
  public static Tensor of(Tensor tensor, int radius, Function<Tensor, ? extends Tensor> function) {
    Objects.requireNonNull(function);
    ImageFilter imageFilter = new ImageFilter(tensor, Integers.requirePositiveOrZero(radius));
    return Array.of(index -> function.apply(imageFilter.block(index)), Dimensions.of(tensor));
  }

  // ---
  private final Tensor tensor;
  private final int radius;
  private final List<Integer> dimensions;

  private ImageFilter(Tensor tensor, int radius) {
    this.tensor = tensor;
    this.radius = radius;
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
