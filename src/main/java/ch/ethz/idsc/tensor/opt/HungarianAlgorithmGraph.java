// code by Samuel J. Stauber
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.PadRight;
import ch.ethz.idsc.tensor.alg.Transpose;

/* package */ abstract class HungarianAlgorithmGraph implements HungarianAlgorithm {
  private final int rowDim;
  private final int colDim;
  protected final Scalar[][] matrix;
  protected int[] xMatch;
  protected int[] yMatch;
  protected int matchCount = 0;

  public HungarianAlgorithmGraph(Tensor _matrix) {
    rowDim = _matrix.length();
    colDim = Unprotect.dimension1(_matrix);
    int dim = Math.max(rowDim, colDim);
    Tensor normal = PadRight.zeros(dim, dim).apply(rowDim <= colDim ? _matrix.copy() : Transpose.of(_matrix));
    matrix = ScalarArray.ofMatrix(normal);
    xMatch = new int[dim];
    Arrays.fill(xMatch, UNASSIGNED);
    yMatch = new int[dim];
    Arrays.fill(yMatch, UNASSIGNED);
  }

  protected final void match(int x, int y) {
    xMatch[x] = y;
    yMatch[y] = x;
    ++matchCount;
  }

  @Override // from HungarianAlgorithm
  public final int[] matching() {
    int[] result = new int[rowDim];
    int[] resvec = rowDim <= colDim ? xMatch : yMatch;
    for (int x = 0; x < rowDim; ++x) {
      int y = resvec[x];
      result[x] = colDim <= y ? UNASSIGNED : y;
    }
    return result;
  }

  @Override // from HungarianAlgorithm
  public final Scalar minimum() {
    Stream<Scalar> stream = rowDim <= colDim //
        ? IntStream.range(0, rowDim).mapToObj(i -> matrix[i][xMatch[i]]) //
        : IntStream.range(0, rowDim).filter(i -> yMatch[i] < colDim).mapToObj(i -> matrix[yMatch[i]][i]);
    return stream.reduce(Scalar::add).get();
  }
}