// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantityTensorTest extends TestCase {
  public void testSimple() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor nuvec = QuantityTensor.of(vector, Unit.of("m*kg^2"));
    assertEquals(nuvec, //
        Tensors.fromString("{1[kg^2*m], 2[kg^2*m], 3[kg^2*m]}", Quantity::fromString));
  }

  public void testFail() {
    Scalar q = Quantity.of(1, "s");
    try {
      QuantityTensor.of(q, Unit.of("m*kg^2"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
