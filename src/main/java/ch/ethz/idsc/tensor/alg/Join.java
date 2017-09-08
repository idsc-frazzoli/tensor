// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** Join has the functionality of joining tensors along a given dimension.
 * 
 * <p>For instance, for matrices A and B
 * <pre>
 * Join.of(0, A, B) is MATLAB::vertcat(A, B) == [A ; B]
 * Join.of(1, A, B) is MATLAB::horzcat(A, B) == [A B]
 * </pre>
 * 
 * <code>Mathematica::Join[0, 1]<code> of one, two or more scalars is <em>not</em> defined.
 * The tensor library also does <em>not</em> permit joining scalars, but only tensors with rank 1 or higher.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Join.html">Join</a> */
public enum Join {
  ;
  /** Example:
   * <pre>
   * Join[1, {{a, b}, {c}}, {{d, e}, {f, g}}] == {{a, b, d, e}, {c, f, g}}
   * </pre>
   * 
   * @param level
   * @param tensors
   * @return joins tensors along dimension level */
  public static Tensor of(int level, Tensor... tensors) {
    return of(level, Arrays.asList(tensors));
  }

  /** Example:
   * <pre>
   * Join.of(Tensors.vector(2, 3, 4), Tensors.vector(9, 8)) == Tensors.vector(2, 3, 4, 9, 8)
   * </pre>
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
    if (list.stream().anyMatch(Tensor::isScalar))
      throw new RuntimeException();
    return Tensor.of(list.stream().flatMap(tensor -> tensor.stream()));
  }
}
