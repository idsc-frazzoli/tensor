// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class ParallelizeTest extends TestCase {
  public void testDotSimple() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(-3, 4, -8);
    assertEquals(a.dot(b), Parallelize.dot(a, b));
  }

  public void testDotEmpty() {
    Tensor a = Tensors.empty();
    Tensor b = Tensors.empty();
    assertEquals(a.dot(b), Parallelize.dot(a, b));
  }

  public void testMatMat() {
    Tensor a = RandomVariate.of(NormalDistribution.standard(), 3, 3);
    Tensor b = HilbertMatrix.of(3, 4);
    Tensor m1 = Parallelize.dot(a, b);
    Tensor m2 = a.dot(b);
    assertEquals(m1, m2);
  }

  public void testDotFail() {
    try {
      Parallelize.dot(RealScalar.ONE, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Parallelize.dot(Tensors.vector(1, 2, 3), HilbertMatrix.of(4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrix() {
    Tensor a = Parallelize.matrix((i, j) -> RationalScalar.of(i, j + 1), 30, 30);
    Tensor b = Tensors.matrix((i, j) -> RationalScalar.of(i, j + 1), 30, 30);
    assertEquals(a, b);
  }
}
