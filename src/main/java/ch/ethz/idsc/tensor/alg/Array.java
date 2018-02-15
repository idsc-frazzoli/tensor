// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** The implementation is consistent with Mathematica.
 * Special examples:
 * <pre>
 * Array[0 &, {0, 1}] == {}
 * Array.zeros(0, 1) == {}
 * Array[0 &, {1, 0, 1}] == {{}}
 * Array.zeros(1, 0, 1) == {{}}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Array.html">Array</a> */
public enum Array {
  ;
  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, Integer... dimensions) {
    return of(function, Arrays.asList(dimensions));
  }

  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions) {
    return of(function, dimensions, Collections.emptyList());
  }

  /** @param dimensions
   * @return tensor of {@link RealScalar#ZERO} with given dimensions
   * @throws Exception if any of the integer parameters is negative */
  public static Tensor zeros(List<Integer> dimensions) {
    if (dimensions.size() == 0)
      return RealScalar.ZERO;
    int length = requirePositiveOrZero(dimensions.get(0));
    return Tensor.of(IntStream.range(0, length) //
        .mapToObj(i -> zeros(dimensions.subList(1, dimensions.size()))));
  }

  /** Careful:
   * {@link #zeros(Integer...)} is not consistent with MATLAB::zeros.
   * In the tensor library, the number of integer parameters equals the rank
   * of the returned tensor. In Matlab this is not the case.
   * 
   * Examples:
   * <pre>
   * Array.zeros(3) == Tensors.vector(0, 0, 0) == {0, 0, 0}
   * Array.zeros(2, 3) == {{0, 0, 0}, {0, 0, 0}}
   * </pre>
   * 
   * @param dimensions
   * @return tensor of {@link RealScalar#ZERO} with given dimensions
   * @throws Exception if any of the integer parameters is negative */
  public static Tensor zeros(Integer... dimensions) {
    return zeros(Arrays.asList(dimensions));
  }

  // helper function
  private static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions, List<Integer> index) {
    int level = index.size();
    if (level == dimensions.size())
      return function.apply(index);
    Tensor tensor = Tensors.empty();
    List<Integer> copy = new ArrayList<>(index);
    copy.add(-1);
    int length = requirePositiveOrZero(dimensions.get(level));
    for (int count = 0; count < length; ++count) {
      copy.set(level, count);
      tensor.append(of(function, dimensions, copy));
    }
    return tensor;
  }

  private static int requirePositiveOrZero(int length) {
    if (length < 0)
      throw new IllegalArgumentException("" + length);
    return length;
  }
}
