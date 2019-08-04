// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
   * @param dimensions with non-negative entries
   * @return tensor with given dimensions and entries as function(index)
   * @throws Exception if any dimension is negative */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions) {
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
    int length = dimensions.get(level);
    Tensor tensor = Tensors.reserve(length);
    for (int count = 0; count < length; ++count) {
      index.set(level, count);
      tensor.append(of(function, level + 1, dimensions, index));
    }
    return tensor;
  }

  /***************************************************/
  public static Tensor fill(Supplier<? extends Tensor> supplier, List<Integer> dimensions) {
    if (dimensions.isEmpty())
      return supplier.get();
    return _fill(supplier, dimensions);
  }

  public static Tensor fill(Supplier<? extends Tensor> supplier, Integer... dimensions) {
    return _fill(supplier, Arrays.asList(dimensions));
  }

  // helper function
  /* package */ static Tensor _fill(Supplier<? extends Tensor> supplier, List<Integer> dimensions) {
    int length = StaticHelper.requirePositiveOrZero(dimensions.get(0));
    if (dimensions.size() == 1)
      return Tensor.of(Stream.generate(supplier).limit(length));
    List<Integer> subList = dimensions.subList(1, dimensions.size());
    return Tensor.of(Stream.generate(() -> _fill(supplier, subList)).limit(length));
  }

  /***************************************************/
  /** @param dimensions
   * @return tensor of {@link RealScalar#ZERO} with given dimensions
   * @throws Exception if any of the integer parameters is negative */
  public static Tensor zeros(List<Integer> dimensions) {
    if (dimensions.isEmpty())
      return RealScalar.ZERO;
    return _fill(() -> RealScalar.ZERO, dimensions);
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
