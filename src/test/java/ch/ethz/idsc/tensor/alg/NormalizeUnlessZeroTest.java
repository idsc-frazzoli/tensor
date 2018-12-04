// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Frobenius;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Total;
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
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail2() {
    TensorUnaryOperator normalize = NormalizeUnlessZero.with(Frobenius.NORM::ofVector);
    try {
      normalize.apply(Array.zeros(3, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizeTotal() {
    TensorUnaryOperator tensorUnaryOperator = NormalizeUnlessZero.with(v -> Total.of(v).Get());
    Tensor tensor = tensorUnaryOperator.apply(Tensors.vector(-1, 3, 2));
    assertEquals(tensor, Tensors.fromString("{-1/4, 3/4, 1/2}"));
    Tensor vector = Tensors.vector(-1, 3, -2);
    Tensor result = tensorUnaryOperator.apply(vector);
    assertEquals(vector, result);
    assertTrue(ExactScalarQ.all(result));
  }
}
