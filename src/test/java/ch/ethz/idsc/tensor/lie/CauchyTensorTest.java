// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.ArrayQ;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class CauchyTensorTest extends TestCase {
  private static void _check(int n) {
    Tensor hilbert = HilbertMatrix.of(n);
    Tensor matrix = CauchyTensor.of(Tensors.vector(i -> RationalScalar.of(2 * i + 1, 2), n), 2);
    assertEquals(matrix, hilbert);
  }

  public void testHilbert() {
    for (int n = 1; n < 6; ++n)
      _check(n);
  }

  public void testRank() {
    assertTrue(ArrayQ.ofRank(CauchyTensor.of(Tensors.vector(1, 2, 3, 4), 3), 3));
    assertTrue(ArrayQ.ofRank(CauchyTensor.of(Tensors.vector(1, 2, 3), 4), 4));
  }

  public void testFail() {
    try {
      CauchyTensor.of(RealScalar.ONE, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      CauchyTensor.of(Tensors.fromString("{{1,2}}"), 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
