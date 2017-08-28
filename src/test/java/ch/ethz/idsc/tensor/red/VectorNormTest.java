// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class VectorNormTest extends TestCase {
  public void testOdd() {
    Tensor tensor = Tensors.vector(2.3, 1.0, 3.2);
    Scalar n = VectorNorm.with(1.5).ofVector(tensor);
    // 4.7071
    assertEquals(n, RealScalar.of(4.707100665786122));
  }

  public void testNormP() {
    Scalar n = VectorNorm.with(1.23).ofVector(Tensors.vector(1, 2, 3));
    assertEquals(n, RealScalar.of(4.982125211204371));
  }

  public void testNormalize() {
    VectorNormInterface vni = VectorNorm.with(2.6);
    Tensor nrm = Normalize.of(Tensors.vector(1, 2, 3), vni);
    assertTrue(Chop._15.close(vni.ofVector(nrm), RealScalar.ONE));
  }

  public void testNormPFail() {
    try {
      VectorNorm.with(0.99).ofVector(Tensors.vector(1, 2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    VectorNormInterface vni = VectorNorm.with(2.6);
    try {
      vni.ofVector(IdentityMatrix.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
