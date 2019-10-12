// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class OrderedQTest extends TestCase {
  public void testCornerCases() {
    assertTrue(OrderedQ.of(Tensors.empty()));
    assertTrue(OrderedQ.of(Tensors.vector(1123)));
  }

  public void testSimple() {
    assertTrue(OrderedQ.of(Tensors.vector(1, 1, 2, 4, 4, 4)));
    assertFalse(OrderedQ.of(Tensors.vector(0, 3, 1)));
  }

  public void testRequire() {
    OrderedQ.require(Tensors.vector(1, 1, 2, 4, 4, 4));
    try {
      OrderedQ.require(Tensors.vector(0, 3, 1));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
