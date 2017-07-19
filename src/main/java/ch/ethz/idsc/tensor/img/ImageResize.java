// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ImageResize.html">ImageResize</a> */
public enum ImageResize {
  ;
  /** function uses nearest neighbor interpolation
   * 
   * @param tensor
   * @param factor positive integer
   * @return */
  public static Tensor nearest(Tensor tensor, int factor) {
    if (factor <= 0)
      throw new RuntimeException();
    List<Integer> list = Dimensions.of(tensor);
    int limit = Math.max(list.get(0), list.get(1)) * factor;
    // precomputation of indices
    int[] index = IntStream.range(0, limit).map(i -> i / factor).toArray();
    return Tensors.matrix((i, j) -> tensor.get(index[i], index[j]), //
        list.get(0) * factor, //
        list.get(1) * factor);
  }
}
