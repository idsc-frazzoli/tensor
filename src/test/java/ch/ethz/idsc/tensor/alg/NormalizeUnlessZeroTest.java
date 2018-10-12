// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Frobenius;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class NormalizeUnlessZeroTest extends TestCase {
  public void testOk1() {
    Tensor v = Tensors.vector(0, 0, 0, 0);
    assertEquals(v, NormalizeUnlessZero.with(Norm._2::ofVector).apply(v));
    for (Norm n : Norm.values())
      assertEquals(v, NormalizeUnlessZero.with(n::ofVector).apply(v));
  }

  public void testNormalizeNaN() {
    Tensor vector = Tensors.of(RealScalar.ONE, DoubleScalar.INDETERMINATE, RealScalar.ONE);
    try {
      NormalizeUnlessZero.with(Norm._2::ofVector).apply(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail2() {
    TensorUnaryOperator normalize = NormalizeUnlessZero.with(Frobenius.NORM::ofVector);
    try {
      normalize.apply(Array.zeros(3, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
