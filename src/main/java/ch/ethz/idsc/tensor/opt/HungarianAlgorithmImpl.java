// code by Samuel J. Stauber
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Min;

/* package */ class HungarianAlgorithmImpl extends HungarianAlgorithmGraph {
  private final HungarianAlgorithmTree hungarianAlgorithmTree;
  private final Set<Integer> freeX = new HashSet<>();
  private final Set<Integer> freeY = new HashSet<>();
  private int iterations = 0;

  public HungarianAlgorithmImpl(Tensor _matrix) {
    super(_matrix);
    // ---
    Scalar[] xLabel = Stream.of(matrix) //
        .map(vector -> Stream.of(vector).reduce(Min::of).get()) //
        .toArray(Scalar[]::new);
    hungarianAlgorithmTree = new HungarianAlgorithmTree(xLabel, yMatch, matrix);
    // ---
    setInitialMatching(xLabel);
    initializeFreeNodes();
    // ---
    while (!isSolved()) {
      int x = pickFreeX();
      int y = hungarianAlgorithmTree.addS(x);
      augmentMatching(x, y);
      hungarianAlgorithmTree.clear();
      ++iterations;
    }
  }

  private void setInitialMatching(Scalar[] xLabel) {
    for (int x = 0; x < xMatch.length; ++x)
      if (xMatch[x] == UNASSIGNED) {
        Scalar xValue = xLabel[x];
        for (int y = 0; y < yMatch.length; ++y)
          if (yMatch[y] == UNASSIGNED && //
              matrix[x][y].equals(xValue)) { // one condition superfluous
            match(x, y);
            break;
          }
      }
  }

  private void initializeFreeNodes() {
    IntStream.range(0, xMatch.length).filter(i -> xMatch[i] == UNASSIGNED).forEach(freeX::add);
    IntStream.range(0, yMatch.length).filter(i -> yMatch[i] == UNASSIGNED).forEach(freeY::add);
  }

  private void augmentMatching(int stoppingX, int startingY) {
    int x;
    int y = startingY;
    do {
      x = hungarianAlgorithmTree.escapeFromY(y);
      match(x, y);
      y = hungarianAlgorithmTree.escapeFromX(x);
    } while (x != stoppingX);
    freeX.remove(stoppingX);
    freeY.remove(startingY);
  }

  private boolean isSolved() {
    return freeX.isEmpty();
  }

  private int pickFreeX() {
    int x = freeX.stream().findFirst().get();
    hungarianAlgorithmTree.setAlpha(x);
    return x;
  }

  @Override // from HungarianAlgorithm
  public final int iterations() {
    return iterations;
  }
}
