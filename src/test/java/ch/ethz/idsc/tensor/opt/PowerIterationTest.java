// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.lie.RotationMatrix;
import ch.ethz.idsc.tensor.mat.Eigensystem;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PowerIterationTest extends TestCase {
  public void testSymmetric() {
    Tensor m = Tensors.fromString("{{2, 3, 0, 1}, {3, 1, 7, 5}, {0, 7, 10, 9}, {1, 5, 9, 13}}");
    Eigensystem eigsys = Eigensystem.ofSymmetric(m);
    Tensor v = eigsys.vectors().get(0).unmodifiable();
    Tensor x = PowerIteration.of(m);
    assertTrue(Chop._12.close(x.dot(v).Get().abs(), RealScalar.ONE));
  }

  public void testNegative() {
    Tensor m = Tensors.fromString("{{-1, 0}, {0, 0}}");
    Tensor x = PowerIteration.of(m);
    assertEquals(x.Get(0).abs(), RealScalar.ONE);
    assertEquals(x.Get(1).abs(), RealScalar.ZERO);
  }

  public void testScalar() {
    Tensor m = Tensors.fromString("{{2}}");
    Eigensystem eigsys = Eigensystem.ofSymmetric(m);
    Tensor v = eigsys.vectors().get(0).unmodifiable();
    assertEquals(v.Get(0).abs(), RealScalar.ONE);
    Tensor x = PowerIteration.of(m);
    assertEquals(x.Get(0).abs(), RealScalar.ONE);
  }

  public void testRotationFail() {
    try {
      PowerIteration.of(RotationMatrix.of(DoubleScalar.of(.3)));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeroFail() {
    try {
      PowerIteration.of(Array.zeros(3, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
