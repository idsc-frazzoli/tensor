// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class DeriveTest extends TestCase {
  public void testSimple() {
    Tensor coeffs = Tensors.vector(-3, 4, -5, 8, 1);
    Tensor result = Derive.of(coeffs);
    ExactTensorQ.require(result);
    assertEquals(result, Tensors.vector(4, -5 * 2, 8 * 3, 1 * 4));
  }

  public void testEmpty() {
    assertEquals(Derive.of(Tensors.vector()), Tensors.vector());
    assertEquals(Derive.of(Tensors.vector(3)), Tensors.empty());
  }

  public void testMatrixFail() {
    try {
      Derive.of(HilbertMatrix.of(4, 5));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Derive.of(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
