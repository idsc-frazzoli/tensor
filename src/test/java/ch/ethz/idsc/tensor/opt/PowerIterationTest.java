// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.lie.RotationMatrix;
import ch.ethz.idsc.tensor.mat.Eigensystem;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PowerIterationTest extends TestCase {
  public void testSymmetric() {
    Tensor matrix = Tensors.fromString("{{2, 3, 0, 1}, {3, 1, 7, 5}, {0, 7, 10, 9}, {1, 5, 9, 13}}");
    Eigensystem eigensystem = Eigensystem.ofSymmetric(matrix);
    Tensor v = eigensystem.vectors().get(0).unmodifiable();
    Tensor x = PowerIteration.of(matrix).get();
    assertTrue(Chop._12.close(x.dot(v).Get().abs(), RealScalar.ONE));
  }

  public void testNegative() {
    Tensor matrix = Tensors.fromString("{{-1, 0}, {0, 0}}");
    Tensor x = PowerIteration.of(matrix).get();
    assertEquals(x.Get(0).abs(), RealScalar.ONE);
    assertEquals(x.Get(1).abs(), RealScalar.ZERO);
  }

  public void testScalar() {
    Tensor matrix = Tensors.fromString("{{2}}");
    Eigensystem eigensystem = Eigensystem.ofSymmetric(matrix);
    Tensor v = eigensystem.vectors().get(0).unmodifiable();
    assertEquals(v.Get(0).abs(), RealScalar.ONE);
    Tensor x = PowerIteration.of(matrix).get();
    assertEquals(x.Get(0).abs(), RealScalar.ONE);
  }

  public void testRotationFail() {
    assertFalse(PowerIteration.of(RotationMatrix.of(DoubleScalar.of(0.3))).isPresent());
  }

  public void testZerosFail() {
    try {
      PowerIteration.of(Array.zeros(3, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVectorFail() {
    try {
      PowerIteration.of(Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      PowerIteration.of(HilbertMatrix.of(4, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      PowerIteration.of(HilbertMatrix.of(3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
