// code by Samuel J. Stauber
// adapted by jph
package ch.ethz.idsc.tensor.opt.hun;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.PadRight;
import ch.ethz.idsc.tensor.alg.Transpose;

/* package */ abstract class HungarianAlgorithmGraph implements HungarianAlgorithm {
  private final int rows;
  private final int cols;
  protected final Scalar[][] matrix;
  protected int[] xMatch;
  protected int[] yMatch;
  protected int matchCount = 0;

  public HungarianAlgorithmGraph(Tensor _matrix) {
    rows = _matrix.length();
    cols = Unprotect.dimension1(_matrix);
    int dim = Math.max(rows, cols);
    Tensor normal = PadRight.zeros(dim, dim).apply(rows <= cols ? _matrix.copy() : Transpose.of(_matrix));
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
    int[] result = new int[rows];
    int[] resvec = rows <= cols ? xMatch : yMatch;
    for (int x = 0; x < rows; ++x) {
      int y = resvec[x];
      result[x] = cols <= y ? UNASSIGNED : y;
    }
    return result;
  }

  @Override // from HungarianAlgorithm
  public final Scalar minimum() {
    Stream<Scalar> stream = rows <= cols //
        ? IntStream.range(0, rows).mapToObj(i -> matrix[i][xMatch[i]]) //
        : IntStream.range(0, rows).filter(i -> yMatch[i] < cols).mapToObj(i -> matrix[yMatch[i]][i]);
    return stream.reduce(Scalar::add).get();
  }
}