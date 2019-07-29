// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;

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
    int size = dimensions.size();
    if (size == 0)
      return RealScalar.ZERO;
    int length = StaticHelper.requirePositiveOrZero(dimensions.get(0));
    return Tensor.of(Stream.generate(() -> zeros(dimensions.subList(1, size))).limit(length));
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

  /** MATLAB::repmat
   * 
   * @param entry non-null
   * @param dimensions
   * @return unmodifiable tensor with given dimensions and entries as given entry */
  public static Tensor repmat(Tensor entry, Integer... dimensions) {
    Tensor tensor = entry.copy();
    for (int index = dimensions.length - 1; 0 <= index; --index)
      tensor = Unprotect.unmodifiable(Collections.nCopies(dimensions[index], tensor));
    return tensor;
  }

  // helper function
  private static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions, List<Integer> index) {
    int level = index.size();
    if (level == dimensions.size())
      return function.apply(index);
    List<Integer> copy = new ArrayList<>(index);
    copy.add(-1);
    int length = dimensions.get(level);
    Tensor tensor = Unprotect.empty(length);
    for (int count = 0; count < length; ++count) {
      copy.set(level, count);
      tensor.append(of(function, dimensions, copy));
    }
    return tensor;
  }
}
