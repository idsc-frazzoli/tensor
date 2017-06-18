// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** Join has the functionality of joining tensors along a given dimension.
 * 
 * <p>For instance, for matrices A and B
 * <ul>
 * <li>Join.of(0, A, B) is MATLAB::vertcat(A, B) == [A ; B]
 * <li>Join.of(1, A, B) is MATLAB::horzcat(A, B) == [A B]
 * </ul>
 * 
 * Mathematica::Join[0, 1] of one, two or more scalars is not defined.
 * The tensor library also does not permit joining scalars, but only tensors with rank 1 or higher.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Join.html">Join</a> */
public enum Join {
  ;
  /** @param level
   * @param tensors
   * @return joins tensors along dimension level */
  public static Tensor of(int level, Tensor... tensors) {
    return of(level, Arrays.asList(tensors));
  }

  /** Example:
   * Join.of(Tensors.vector(2, 3, 4), Tensors.vector(9, 8)) == Tensors.vector(2, 3, 4, 9, 8)
   * 
   * @param tensors
   * @return joins elements of all tensors along their first dimension */
  public static Tensor of(Tensor... tensors) {
    return of(0, Arrays.asList(tensors));
  }

  /** @param level
   * @param list
   * @return joins tensors in the list along dimension level */
  public static Tensor of(int level, List<Tensor> list) {
    return MapThread.of(Join::_flatten, list, level);
  }

  // helper function called in base case of more general function of(...)
  private static Tensor _flatten(List<Tensor> list) {
    if (list.stream().filter(Tensor::isScalar).findAny().isPresent())
      throw new RuntimeException();
    return Tensor.of(list.stream().flatMap(tensor -> tensor.flatten(0)));
  }
}
