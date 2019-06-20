// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class FirstPositionTest extends TestCase {
  public void testSimple() {
    Tensor tensor = Tensors.vector(5, 6, 7, 8, 9);
    assertEquals(FirstPosition.of(tensor, RealScalar.of(5)).getAsInt(), 0);
    assertEquals(FirstPosition.of(tensor, RealScalar.of(6)).getAsInt(), 1);
    assertEquals(FirstPosition.of(tensor, RealScalar.of(7)).getAsInt(), 2);
    assertEquals(FirstPosition.of(tensor, RealScalar.of(8)).getAsInt(), 3);
    assertEquals(FirstPosition.of(tensor, RealScalar.of(9)).getAsInt(), 4);
    assertFalse(FirstPosition.of(tensor, RealScalar.of(10)).isPresent());
  }

  public void testFailScalar() {
    try {
      FirstPosition.of(RealScalar.of(7), RealScalar.of(7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailTensorNull() {
    try {
      FirstPosition.of(null, RealScalar.of(7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailElementNull() {
    try {
      FirstPosition.of(Tensors.vector(5, 6, 7, 8, 9), null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
