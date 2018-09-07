// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantityTensorTest extends TestCase {
  public void testScalar() {
    Tensor tensor = QuantityTensor.of(RealScalar.ONE, Unit.of("N"));
    assertEquals(tensor, Quantity.of(1, "N"));
  }

  public void testVector() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor nuvec = QuantityTensor.of(vector, Unit.of("m*kg^2"));
    assertEquals(nuvec, //
        Tensors.fromString("{1[kg^2*m], 2[kg^2*m], 3[kg^2*m]}"));
  }

  public void testExample() {
    Tensor vector = Tensors.vector(2, 3, -1);
    Tensor nuvec = QuantityTensor.of(vector, Unit.of("m*s^-1"));
    assertEquals(nuvec, //
        Tensors.fromString("{2[m*s^-1], 3.0[m * s^+1 * s^-2], -1.0[s^-1 * m]}"));
  }

  public void testFail() {
    Scalar q = Quantity.of(1, "s");
    try {
      QuantityTensor.of(q, Unit.of("m*kg^2"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      QuantityTensor.of(Tensors.of(q, q), Unit.of("m*kg^2"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
