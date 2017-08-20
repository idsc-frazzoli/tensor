// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class NylanderPowerTest extends TestCase {
  public void testZero() {
    for (int exp = 0; exp < 5; ++exp)
      assertEquals(NylanderPower.of(Tensors.vector(0, 0, 0), exp), Tensors.vector(0, 0, exp == 0 ? 1 : 0));
  }

  public void testEZero() {
    Distribution distribution = NormalDistribution.standard();
    for (int n = 0; n < 20; ++n) {
      assertEquals( //
          NylanderPower.of(RandomVariate.of(distribution, 3), 0), Tensors.vector(0, 0, 1));
    }
  }

  public void testSpecial() {
    Tensor expected = Tensors.vector(-78.82060109268512, 32.91199889151254, -44.74536203838224);
    Tensor actual = NylanderPower.of(Tensors.vector(1.12, -2.23, 3.34), 3.2);
    assertTrue(Chop._10.close(expected, actual));
  }

  public void testSpecial8() {
    Tensor expected = Tensors.vector(69616.37623576757, 45802.24276062281, 37310.875885353504);
    Tensor actual = NylanderPower.of(Tensors.vector(1.12, -2.23, 3.34), 8);
    assertTrue(Chop._08.close(expected, actual));
  }

  public void testFail() {
    try {
      NylanderPower.of(Tensors.vector(1, 2, 3, 4), 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
