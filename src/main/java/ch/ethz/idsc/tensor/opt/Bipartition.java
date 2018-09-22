// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Set;
import java.util.stream.Stream;

public interface Bipartition {
  void add(int index);

  Stream<Integer> nodesStream();

  Set<Integer> notNodes();

  void clear();
}
