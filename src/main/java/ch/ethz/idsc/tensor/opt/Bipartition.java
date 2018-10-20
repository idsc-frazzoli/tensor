// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Set;
import java.util.stream.Stream;

interface Bipartition {
  /** @param index to add as node */
  void add(int index);

  /** @return nodes */
  Stream<Integer> nodesStream();

  /** @return */
  Set<Integer> notNodes();

  /** empty nodes */
  void clear();
}
