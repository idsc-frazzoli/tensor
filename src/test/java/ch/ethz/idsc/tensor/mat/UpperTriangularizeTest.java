// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Diagonal;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class UpperTriangularizeTest extends TestCase {
  public void testIncludingDiagonal() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    Tensor actual = Tensors.fromString("{{1, 2, 3}, {0, 5, 6}, {0, 0, 9}, {0, 0, 0}}");
    assertEquals(UpperTriangularize.of(matrix), actual);
  }

  public void testAboveDiagonal() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    Tensor actual = Tensors.fromString("{{0, 2, 3}, {0, 0, 6}, {0, 0, 0}, {0, 0, 0}}");
    assertEquals(UpperTriangularize.of(matrix, 1), actual);
  }

  public void testFull() {
    Distribution distribution = NormalDistribution.standard();
    Tensor matrix = RandomVariate.of(distribution, 10, 10);
    Tensor result = Total.of(Tensors.of( //
        LowerTriangularize.of(matrix, -1), //
        DiagonalMatrix.with(Diagonal.of(matrix)), //
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

  public void testEmpty() {
    Tensor matrix = Tensors.fromString("{{}}");
    for (int k = -2; k < 3; ++k) {
      assertEquals(matrix, LowerTriangularize.of(matrix, k));
      assertEquals(matrix, UpperTriangularize.of(matrix, k));
    }
  }

  public void testGaussScalar() {
    Tensor matrix = Tensors.matrix((i, j) -> GaussScalar.of(2 * i + j + 1, 7), 3, 4);
    for (int k = -3; k < 5; ++k) {
      Tensor lower = LowerTriangularize.of(matrix, k);
      Tensor upper = UpperTriangularize.of(matrix, k + 1);
      assertEquals(matrix, lower.add(upper));
    }
  }

  public void testFail() {
    try {
      UpperTriangularize.of(RealScalar.ONE, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UpperTriangularize.of(LieAlgebras.heisenberg3(), 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
