// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class OrthogonalizeTest extends TestCase {
  private static void _check(Tensor matrix) {
    Tensor q = Orthogonalize.of(matrix);
    assertEquals(Dimensions.of(matrix), Dimensions.of(q));
    // System.out.println(Pretty.of(q));
    assertTrue(OrthogonalMatrixQ.of(q));
  }

  public void testSimple1() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1}}");
    assertFalse(OrthogonalMatrixQ.of(matrix));
    _check(matrix);
  }

  public void testSimple2() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1}, {1, 1, 1}}");
    assertFalse(OrthogonalMatrixQ.of(matrix));
    _check(matrix);
  }

  public void testRandom() {
    Distribution distribution = NormalDistribution.of();
    Tensor matrix = RandomVariate.of(distribution, 10, 10);
    _check(matrix);
  }

  public void testFailVector() {
    try {
      Orthogonalize.of(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Tensor matrix = Transpose.of(Tensors.fromString("{{1, 0, 1}, {1, 1, 1}}"));
      Orthogonalize.of(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
