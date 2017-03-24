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
  private static List<Set<Integer>> _sets(Tensor tensor, int level, List<Set<Integer>> sets) {
    if (sets.size() <= level)
      sets.add(new HashSet<>());
    sets.get(level).add(tensor.length());
    if (tensor.length() != Scalar.LENGTH)
      tensor.flatten(0).forEach(entry -> _sets(entry, level + 1, sets));
    return sets;
  }

  private static List<Set<Integer>> sets(Tensor tensor) {
    return _sets(tensor, 0, new ArrayList<>());
  }

  /** @return dimensions of this tensor */
  public static final List<Integer> of(Tensor tensor) {
    List<Integer> ret = new ArrayList<>();
    for (Set<Integer> set : sets(tensor))
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
  public static final boolean isArray(Tensor tensor) {
    return !sets(tensor).stream() //
        .map(Set::size).filter(size -> !size.equals(1)).findAny().isPresent();
  }
}
