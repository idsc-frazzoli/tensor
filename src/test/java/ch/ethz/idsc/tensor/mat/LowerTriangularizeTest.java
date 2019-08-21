// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import junit.framework.TestCase;

public class LowerTriangularizeTest extends TestCase {
  public void test1x1() {
    Tensor matrix = Tensors.fromString("{{1}}");
    for (int k = -3; k <= 3; ++k)
      assertEquals(Tensors.fromString("{{" + (0 <= k ? 1 : 0) + "}}"), LowerTriangularize.of(matrix, k));
  }

  public void testIncludingDiagonal() {
    Tensor matrix = Tensors.fromString("{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {9, 5, 2}}");
    Tensor actual = Tensors.fromString("{{1, 0, 0}, {4, 5, 0}, {7, 8, 9}, {9, 5, 2}}");
    assertEquals(LowerTriangularize.of(matrix), actual);
  }

  public void testSubDiagonal() {
    Tensor matrix = Tensors.fromString("{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {9, 5, 2}}");
    Tensor actual = Tensors.fromString("{{0, 0, 0}, {4, 0, 0}, {7, 8, 0}, {9, 5, 2}}");
    assertEquals(LowerTriangularize.of(matrix, -1), actual);
  }

  public void testScalarFail() {
    try {
      LowerTriangularize.of(RealScalar.ONE, 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRank3Fail() {
    try {
      LowerTriangularize.of(LieAlgebras.he1(), 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
