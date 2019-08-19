// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class NuttallWindowTest extends TestCase {
  public void testZero() {
    ScalarUnaryOperator scalarUnaryOperator = NuttallWindow.FUNCTION;
    assertEquals(scalarUnaryOperator.apply(RealScalar.ZERO), RealScalar.ONE);
  }

  public void testOutside() {
    assertEquals(NuttallWindow.FUNCTION.apply(RealScalar.of(-0.52)), RealScalar.ZERO);
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(NuttallWindow.of(tensor), tensor.map(NuttallWindow.FUNCTION));
  }

  public void testQuantityFail() {
    try {
      NuttallWindow.FUNCTION.apply(Quantity.of(0, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      NuttallWindow.FUNCTION.apply(Quantity.of(2, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
