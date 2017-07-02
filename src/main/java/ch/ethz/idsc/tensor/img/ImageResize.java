// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.List;

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
   * @param factor
   * @return */
  // EXPERIMENTAL API not finalized
  public static Tensor nearest(Tensor tensor, int factor) {
    if (factor <= 0)
      throw new RuntimeException();
    List<Integer> list = Dimensions.of(tensor);
    return Tensors.matrix((i, j) -> tensor.get(i / factor, j / factor), //
        list.get(0) * factor, //
        list.get(1) * factor);
  }
}
