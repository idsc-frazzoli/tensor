// code by jph
package ch.ethz.idsc.tensor.opt.hun;

import java.util.Set;
import java.util.stream.Stream;

/* package */ interface Bipartition {
  /** @param index to add as node */
  void add(int index);

  /** @return nodes */
  Stream<Integer> nodesStream();

  /** @return */
  Set<Integer> notNodes();

  /** empty nodes */
  void clear();
}
