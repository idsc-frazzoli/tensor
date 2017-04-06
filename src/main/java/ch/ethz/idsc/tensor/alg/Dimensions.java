// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dimensions.html">Dimensions</a> */
public enum Dimensions {
  ;
  /** @return dimensions of this tensor */
  public static List<Integer> of(Tensor tensor) {
    List<Integer> ret = new ArrayList<>();
    for (Set<Integer> set : complete(tensor))
      if (set.size() == 1) {
        int val = set.iterator().next(); // get unique element from set
        if (val == Scalar.LENGTH) // has scalar
          break;
        ret.add(val);
      } else
        break;
    return ret;
  }

  /** @return true if tensor structure is identical at all levels, else false.
   * true for {@link Scalar}s */
  public static boolean isArray(Tensor tensor) {
    return !complete(tensor).stream() //
        .map(Set::size) //
        .filter(size -> !size.equals(1)) //
        .findAny().isPresent();
  }

  /** @param tensor
   * @return true if tensor.length() == 0, and
   * false if tensor contains entries or is a {@link Scalar} */
  public static boolean isEmpty(Tensor tensor) { // Marc's function
    return tensor.length() == 0;
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
    if (tensor.length() != Scalar.LENGTH)
      tensor.flatten(0).forEach(entry -> _sets(entry, level + 1, sets));
    return sets;
  }
}
