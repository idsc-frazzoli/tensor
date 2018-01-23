// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ScalarQTest extends TestCase {
  public void testSimple() {
    assertTrue(ScalarQ.of(Quantity.of(3, "m")));
  }

  public void testVector() {
    assertFalse(ScalarQ.of(Tensors.vector(1, 2, 3)));
  }

  public void testThenThrow() {
    ScalarQ.thenThrow(Tensors.vector(1, 2, 3));
    try {
      ScalarQ.thenThrow(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}