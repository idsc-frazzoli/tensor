// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class HermitianMatrixQTest extends TestCase {
  public void testMatrix() {
    assertTrue(HermitianMatrixQ.of(Tensors.fromString("{{0,I},{-I,0}}")));
    assertFalse(HermitianMatrixQ.of(Tensors.fromString("{{I,I},{-I,0}}")));
    assertFalse(HermitianMatrixQ.of(Tensors.fromString("{{0,I},{I,0}}")));
  }

  public void testHilbert() {
    assertTrue(HermitianMatrixQ.of(HilbertMatrix.of(10)));
  }

  public void testNonMatrix() {
    assertFalse(HermitianMatrixQ.of(Tensors.vector(1, 2, 3)));
    assertFalse(HermitianMatrixQ.of(RealScalar.ONE));
  }
}
