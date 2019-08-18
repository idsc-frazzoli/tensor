// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Internal;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

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
   * @param dimensions with non-negative entries
   * @return tensor with given dimensions and entries as function(index)
   * @throws Exception if any dimension is negative */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions) {
    dimensions.forEach(Internal::requirePositiveOrZero);
    return of(function, 0, dimensions, new ArrayList<>(dimensions));
  }

  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions with non-negative entries
   * @return tensor with given dimensions and entries as function(index)
   * @throws Exception if any dimension is negative */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, Integer... dimensions) {
    return of(function, Arrays.asList(dimensions));
  }

  // helper function
  private static Tensor of(Function<List<Integer>, ? extends Tensor> function, int level, List<Integer> dimensions, List<Integer> index) {
    if (level == dimensions.size())
      return function.apply(index);
    return Tensor.of(IntStream.range(0, dimensions.get(level)) //
        .mapToObj(count -> {
          index.set(level, count);
          return of(function, level + 1, dimensions, index);
        }));
  }

  /***************************************************/
  public static Tensor fill(Supplier<? extends Tensor> supplier, List<Integer> dimensions) {
    if (dimensions.isEmpty())
      return supplier.get();
    dimensions.forEach(Internal::requirePositiveOrZero);
    return fill(supplier, 0, dimensions);
  }

  public static Tensor fill(Supplier<? extends Tensor> supplier, Integer... dimensions) {
    return fill(supplier, 0, Arrays.asList(dimensions));
  }

  // helper function
  private static Tensor fill(Supplier<? extends Tensor> supplier, int level, List<Integer> dimensions) {
    int length = dimensions.get(level);
    int next = level + 1;
    return dimensions.size() == next //
        ? Tensor.of(IntStream.range(0, length).mapToObj(i -> supplier.get()))
        : Tensor.of(IntStream.range(0, length).mapToObj(i -> fill(supplier, next, dimensions)));
  }

  /***************************************************/
  /** @param dimensions
   * @return tensor of {@link RealScalar#ZERO} with given dimensions
   * @throws Exception if any of the integer parameters is negative */
  public static Tensor zeros(List<Integer> dimensions) {
    if (dimensions.isEmpty())
      return RealScalar.ZERO;
    dimensions.forEach(Internal::requirePositiveOrZero);
    return fill(() -> RealScalar.ZERO, 0, dimensions);
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
}
