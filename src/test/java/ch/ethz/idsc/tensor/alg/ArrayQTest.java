// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ArrayQTest extends TestCase {
  public void testScalar() {
    assertTrue(ArrayQ.of(RealScalar.ONE));
    assertTrue(ArrayQ.of(ComplexScalar.fromPolar(3.7, 9.8)));
    assertTrue(ArrayQ.of(Quantity.of(4, "m")));
    assertTrue(ArrayQ.ofRank(RealScalar.ONE, 0));
  }

  public void testIsArray() {
    Tensor d = DoubleScalar.of(.12);
    assertTrue(ArrayQ.of(d));
    assertTrue(ArrayQ.of(Tensors.empty()));
    Tensor a = Tensors.vectorLong(3, 2, 3);
    assertTrue(ArrayQ.of(a));
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of(a, b);
    assertFalse(ArrayQ.of(c));
  }

  public void testNullFail() {
    try {
      ArrayQ.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
