// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dimensions.html">Dimensions</a> */
public enum Dimensions {
  ;
  /** Examples:
   * <pre>
   * Dimensions.of[3.14] = {}
   * Dimensions.of[{}] == {0}
   * Dimensions.of[{1, 2, 3}] == {3}
   * Dimensions.of[{{1, 2, 3}, {4, 5, 6}}] == {2, 3}
   * </pre>
   * 
   * @return dimensions of this tensor */
  public static List<Integer> of(Tensor tensor) {
    return list(complete(tensor));
  }

  /***************************************************/
  /** @return true if tensor structure is identical at all levels, else false.
   * true for {@link Scalar}s
   * 
   * @see ArrayQ */
  /* package */ static boolean isArray(Tensor tensor) {
    return check(complete(tensor));
  }

  /* package */ static boolean isArrayWithRank(Tensor tensor, int rank) {
    List<Set<Integer>> complete = complete(tensor);
    return list(complete).size() == rank && check(complete);
  }

  /* package */ static boolean isArrayWithDimensions(Tensor tensor, List<Integer> dims) {
    List<Set<Integer>> complete = complete(tensor);
    return list(complete).equals(dims) && check(complete);
  }

  /* package */ static Optional<Integer> arrayRank(Tensor tensor) {
    List<Set<Integer>> complete = complete(tensor);
    return check(complete) ? Optional.of(list(complete).size()) : Optional.empty();
  }

  /***************************************************/
  private static boolean check(List<Set<Integer>> complete) {
    return complete.stream().mapToInt(Set::size).allMatch(size -> size == 1);
  }

  private static List<Integer> list(List<Set<Integer>> complete) {
    List<Integer> ret = new ArrayList<>();
    for (Set<Integer> set : complete)
      if (set.size() == 1) {
        int val = set.iterator().next(); // get unique element from set
        if (val == Scalar.LENGTH) // has scalar
          break;
        ret.add(val);
      } else
        break;
    return ret;
  }

  /** @param tensor
   * @return list of set of lengths on all levels
   * also includes length of scalars as Scalar.LENGTH == -1 */
  private static List<Set<Integer>> complete(Tensor tensor) {
    return sets(tensor, 0, new ArrayList<>());
  }

  // helper function
  private static List<Set<Integer>> sets(Tensor tensor, int level, List<Set<Integer>> sets) {
    if (sets.size() <= level)
      sets.add(new HashSet<>());
    sets.get(level).add(tensor.length());
    if (!ScalarQ.of(tensor))
      tensor.stream().forEach(entry -> sets(entry, level + 1, sets));
    return sets;
  }
}
