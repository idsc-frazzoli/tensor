// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class UnitizeTest extends TestCase {
  public void testVector() {
    Tensor tensor = Unitize.of(Tensors.vector(0, 0, 1e-3, -3, Double.NaN, 0));
    ExactTensorQ.require(tensor);
    assertEquals(tensor, Tensors.vector(0, 0, 1, 1, 1, 0));
  }

  public void testGaussScalar() {
    assertEquals(Unitize.FUNCTION.apply(GaussScalar.of(0, 13)), RealScalar.ZERO);
    assertEquals(Unitize.FUNCTION.apply(GaussScalar.of(2, 7)), RealScalar.ONE);
  }

  public void testQuantity() {
    assertEquals(Unitize.FUNCTION.apply(Quantity.of(0, "s*m^3")), RealScalar.ZERO);
    assertEquals(Unitize.FUNCTION.apply(Quantity.of(0.123, "s^-2*m^3")), RealScalar.ONE);
  }

  public void testNullFail() {
    try {
      Unitize.FUNCTION.apply(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
