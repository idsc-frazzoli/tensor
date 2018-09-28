// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class RootsFailTest extends TestCase {
  public void testScalarFail() {
    try {
      Roots.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyFail() {
    try {
      Roots.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testOnes() {
    Tensor coeffs = Tensors.vector(0);
    try {
      Roots.of(coeffs);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testConstantZeroFail() {
    try {
      Roots.of(Tensors.vector(0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZerosFail() {
    for (int length = 0; length < 10; ++length)
      try {
        Roots.of(Array.zeros(length));
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
  }

  public void testMatrixFail() {
    try {
      Roots.of(HilbertMatrix.of(2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNotImplemented() {
    try {
      Roots.of(Tensors.vector(1, 2, 3, 4, 5, 6));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
