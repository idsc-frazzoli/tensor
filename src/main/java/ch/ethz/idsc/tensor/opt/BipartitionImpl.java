// code by Samuel J. Stauber and jph
package ch.ethz.idsc.tensor.opt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* package */ class BipartitionImpl implements Bipartition {
  static Bipartition empty(int dim) {
    return new BipartitionImpl(IntStream.range(0, dim).boxed());
  }

  // ---
  private final List<Integer> nodes = new ArrayList<>();
  private final Set<Integer> notNodes;

  private BipartitionImpl(Stream<Integer> stream) {
    this.notNodes = stream.collect(Collectors.toSet());
  }

  @Override
  public void add(int index) {
    nodes.add(index);
    notNodes.remove(index);
  }

  @Override
  public Stream<Integer> nodesStream() {
    return nodes.stream();
  }

  @Override
  public Set<Integer> notNodes() {
    return Collections.unmodifiableSet(notNodes);
  }

  @Override
  public void clear() {
    notNodes.addAll(nodes);
    nodes.clear();
  }
}