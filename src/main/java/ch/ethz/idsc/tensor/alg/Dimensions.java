// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Dimensions.html">Dimensions</a> */
public class Dimensions {
  /** Examples:
   * <pre>
   * Dimensions.of[3.14] = {}
   * Dimensions.of[{}] == {0}
   * Dimensions.of[{1, 2, 3}] == {3}
   * Dimensions.of[{1, 2, {3, 4}}] == {3}
   * Dimensions.of[{{1, 2, 3}, {4, 5, 6}}] == {2, 3}
   * </pre>
   * 
   * @return dimensions of given tensor */
  public static List<Integer> of(Tensor tensor) {
    return new Dimensions(tensor).list();
  }

  // ---
  /** list of set of lengths on all levels also includes length of scalars as Scalar.LENGTH == -1 */
  private final List<Set<Integer>> lengths = new ArrayList<>();

  /** @param tensor */
  public Dimensions(Tensor tensor) {
    build(tensor, 0);
  }

  private void build(Tensor tensor, int level) {
    if (lengths.size() <= level)
      lengths.add(new HashSet<>());
    lengths.get(level).add(tensor.length());
    if (!ScalarQ.of(tensor))
      tensor.stream().forEach(entry -> build(entry, level + 1));
  }

  /** @return dimensions of given tensor
   * @see #of(Tensor) */
  public List<Integer> list() {
    List<Integer> ret = new ArrayList<>();
    for (Set<Integer> set : lengths)
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
   * true for {@link Scalar}s
   * @see ArrayQ */
  public boolean isArray() {
    return lengths.stream().mapToInt(Set::size).allMatch(size -> size == 1);
  }

  /** @return 0 for a scalar, 1 for a vector, etc. */
  public int maxDepth() {
    return lengths.size() - 1;
  }

  /** @param depth
   * @return unmodifiable set of lengths at given depth
   * @throws Exception if depth is not from the set 0, 1, ..., {@link #maxDepth()} */
  public Set<Integer> lengths(int depth) {
    return Collections.unmodifiableSet(lengths.get(depth));
  }
}
