// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class NormInfinityTest extends TestCase {
  public void testQuantity() {
    Scalar qs1 = Quantity.of(-3, "m");
    Scalar qs2 = Quantity.of(-4, "m");
    Scalar qs3 = Quantity.of(4, "m");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm.INFINITY.of(vec), qs3);
  }

  public void testQuantityMixed() {
    Scalar qs1 = Quantity.of(-3, "m");
    Scalar qs2 = Quantity.of(2, "m");
    Tensor vec = Tensors.of(qs1, qs2);
    Scalar nin = Norm.INFINITY.of(vec);
    Scalar act = Quantity.of(3, "m");
    assertEquals(nin, act);
  }

  public void testFail() {
    try {
      Norm.INFINITY.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
