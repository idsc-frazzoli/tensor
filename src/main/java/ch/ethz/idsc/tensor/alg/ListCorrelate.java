// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Total;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListCorrelate.html">ListCorrelate</a> */
public enum ListCorrelate {
  ;
  // ---
  /** ListCorrelate[{x, y}, {a, b, c, d, e, f}] ==
   * {a x + b y, b x + c y, c x + d y, d x + e y, e x + f y}
   * 
   * @param kernel
   * @param tensor
   * @return correlation of kernel with tensor */
  public static Tensor of(Tensor kernel, Tensor tensor) {
    List<Integer> mask = Dimensions.of(kernel);
    List<Integer> size = Dimensions.of(tensor);
    List<Integer> dims = new ArrayList<>();
    for (int index = 0; index < mask.size(); ++index)
      dims.add(size.get(index) - mask.get(index) + 1);
    return Array.of(index -> Total.of(Flatten.of(kernel.pmul(tensor.block(index, mask)), -1)), dims);
  }
}
