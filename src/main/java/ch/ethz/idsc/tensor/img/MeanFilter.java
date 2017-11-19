// code by gjoel
package ch.ethz.idsc.tensor.img;

import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.TensorRank;
import ch.ethz.idsc.tensor.red.Mean;

/** the implementation is consistent with Mathematica.
 * 
 * <p>For images apply the mean filter to each of the RGB channels separately.
 * 
 * <p>{@link MeanFilter} requires less operations for computation than {@link MedianFilter}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MeanFilter.html">MeanFilter</a> */
public enum MeanFilter {
  ;
  /** Example:
   * <pre>
   * MeanFilter.of({-3, 3, 6, 0, 0, 3, -3, -9}, 2) == {0, 2, 3, 2, 1, 0, -3, -6}
   * </pre>
   * 
   * @param tensor of arbitrary rank
   * @param radius non-negative integer
   * @return filtered version of input tensor with same {@link Dimensions};
   * for radius == 0 the function returns a copy of the given tensor */
  public static Tensor of(Tensor tensor, int radius) {
    ScalarQ.thenThrow(tensor);
    if (radius < 0)
      throw new IllegalArgumentException("" + radius);
    int rank = TensorRank.of(tensor);
    UnaryOperator<Tensor> unaryOperator = value -> TensorExtract.convolve(value, radius, Mean::of);
    for (int level = 0; level < rank; ++level)
      tensor = TensorMap.of(unaryOperator, tensor, level);
    return tensor;
  }
}
