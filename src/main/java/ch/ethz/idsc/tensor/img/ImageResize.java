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
    return nearest(tensor, factor, factor);
  }

  /** function uses nearest neighbor interpolation
   * 
   * @param tensor
   * @param fx scaling along x axis
   * @param fy scaling along y axis
   * @return */
  public static Tensor nearest(Tensor tensor, int fx, int fy) {
    if (fx <= 0 || fy <= 0)
      throw new RuntimeException(fx + " " + fy);
    List<Integer> list = Dimensions.of(tensor);
    // precomputation of indices
    int[] ix = IntStream.range(0, list.get(0) * fx).map(i -> i / fx).toArray();
    int[] iy = IntStream.range(0, list.get(1) * fy).map(i -> i / fy).toArray();
    return Tensors.matrix((i, j) -> tensor.get(ix[i], iy[j]), //
        list.get(0) * fx, //
        list.get(1) * fy);
  }
}
