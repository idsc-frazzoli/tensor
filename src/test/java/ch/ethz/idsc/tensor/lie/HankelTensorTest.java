// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import junit.framework.TestCase;

public class HankelTensorTest extends TestCase {
  public void testRank2() {
    Tensor tensor = HankelTensor.of(Tensors.vector(1, 2, 3, 4, 5), 2);
    assertTrue(SymmetricMatrixQ.of(tensor));
  }

  public void testRank3a() {
    Tensor tensor = HankelTensor.of(Tensors.vector(1, 2, 3, 4), 3);
    tensor.stream().forEach(matrix -> assertTrue(SymmetricMatrixQ.of(matrix)));
  }

  public void testRank3b() {
    Tensor tensor = HankelTensor.of(Tensors.vector(0, 1, 2, 3, 4, 5, 6), 3);
    tensor.stream().forEach(matrix -> assertTrue(SymmetricMatrixQ.of(matrix)));
  }

  public void testFailVector() {
    try {
      HankelTensor.of(RealScalar.ONE, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HankelTensor.of(Tensors.fromString("{{1,2}}"), 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank() {
    try {
      HankelTensor.of(Tensors.vector(1, 2, 3, 4, 5, 6), 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
