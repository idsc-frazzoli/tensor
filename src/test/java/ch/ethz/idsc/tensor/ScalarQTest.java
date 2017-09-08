// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ScalarQTest extends TestCase {
  public void testSimple() {
    assertTrue(ScalarQ.of(Quantity.fromString("3[m]")));
  }

  public void testVector() {
    assertFalse(ScalarQ.of(Tensors.vector(1, 2, 3)));
  }
}
