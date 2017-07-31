// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dimensions.html">Dimensions</a> */
public enum Dimensions {
  ;
  /** @return dimensions of this tensor */
  public static List<Integer> of(Tensor tensor) {
    return _list(complete(tensor));
  }

  /** @param tensor
   * @return true if tensor.length() == 0, and
   * false if tensor contains entries or is a {@link Scalar} */
  public static boolean isEmptyTensor(Tensor tensor) { // Marc's function
    return tensor.length() == 0;
  }

  /***************************************************/
  /** @return true if tensor structure is identical at all levels, else false.
   * true for {@link Scalar}s
   * 
   * @see ArrayQ */
  /* package */ static boolean isArray(Tensor tensor) {
    return _isArray(complete(tensor));
  }

  /* package */ static boolean isArrayWithRank(Tensor tensor, int rank) {
    List<Set<Integer>> complete = complete(tensor);
    return _list(complete).size() == rank && _isArray(complete);
  }

  /* package */ static boolean isArrayWithDimensions(Tensor tensor, List<Integer> dims) {
    List<Set<Integer>> complete = complete(tensor);
    return _list(complete).equals(dims) && _isArray(complete);
  }

  // helper function
  private static boolean _isArray(List<Set<Integer>> complete) {
    return !complete.stream().map(Set::size) //
        .anyMatch(size -> !size.equals(1));
  }

  /***************************************************/
  private static List<Integer> _list(List<Set<Integer>> complete) {
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
    return _sets(tensor, 0, new ArrayList<>());
  }

  // helper function
  private static List<Set<Integer>> _sets(Tensor tensor, int level, List<Set<Integer>> sets) {
    if (sets.size() <= level)
      sets.add(new HashSet<>());
    sets.get(level).add(tensor.length());
    if (!tensor.isScalar())
      tensor.flatten(0).forEach(entry -> _sets(entry, level + 1, sets));
    return sets;
  }
}
