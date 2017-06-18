// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListCorrelate.html">ListCorrelate</a> */
public enum ListCorrelate {
  ;
  /** ListCorrelate[{x, y}, {a, b, c, d, e, f}] ==
   * {a x + b y, b x + c y, c x + d y, d x + e y, e x + f y}
   * 
   * @param kernel
   * @param tensor
   * @return correlation of kernel with tensor */
  public static Tensor of(Tensor kernel, Tensor tensor) {
    List<Integer> mask = Dimensions.of(kernel);
    List<Integer> size = Dimensions.of(tensor);
    List<Integer> dimensions = IntStream.range(0, mask.size()).boxed() //
        .map(index -> size.get(index) - mask.get(index) + 1) //
        .collect(Collectors.toList());
    return Array.of(index -> kernel.pmul(tensor.block(index, mask)).flatten(-1) //
        .reduce(Tensor::add).get(), dimensions);
  }
}
