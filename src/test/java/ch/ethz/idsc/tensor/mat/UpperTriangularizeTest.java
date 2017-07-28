// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Diagonal;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class UpperTriangularizeTest extends TestCase {
  public void testSimple0() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    // System.out.println(Pretty.of(UpperTriangularize.of(matrix)));
    // System.out.println(UpperTriangularize.of(matrix));
    Tensor actual = Tensors.fromString("{{1, 2, 3}, {0, 5, 6}, {0, 0, 9}, {0, 0, 0}}");
    assertEquals(UpperTriangularize.of(matrix), actual);
  }

  public void testSimpleP1() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    // System.out.println(UpperTriangularize.of(matrix, 1));
    Tensor actual = Tensors.fromString("{{0, 2, 3}, {0, 0, 6}, {0, 0, 0}, {0, 0, 0}}");
    assertEquals(UpperTriangularize.of(matrix, 1), actual);
  }

  public void testFull() {
    Distribution distribution = NormalDistribution.standard();
    Tensor matrix = RandomVariate.of(distribution, 10, 10);
    Tensor result = Total.of(Tensors.of( //
        LowerTriangularize.of(matrix, -1), //
        DiagonalMatrix.of(Diagonal.of(matrix)), //
        UpperTriangularize.of(matrix, 1)));
    assertEquals(matrix, result);
  }

  public void testRectangular1() {
    Distribution distribution = NormalDistribution.standard();
    Tensor matrix = RandomVariate.of(distribution, 8, 12);
    for (int k = -12; k <= 12; ++k) {
      Tensor result = Total.of(Tensors.of( //
          LowerTriangularize.of(matrix, k), //
          UpperTriangularize.of(matrix, k + 1)));
      assertEquals(matrix, result);
    }
  }

  public void testRectangular2() {
    Distribution distribution = NormalDistribution.standard();
    Tensor matrix = RandomVariate.of(distribution, 12, 8);
    for (int k = -12; k <= 12; ++k) {
      Tensor result = Total.of(Tensors.of( //
          LowerTriangularize.of(matrix, k), //
          UpperTriangularize.of(matrix, k + 1)));
      assertEquals(matrix, result);
    }
  }
}
