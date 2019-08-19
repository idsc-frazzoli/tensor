// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class HammingWindowTest extends TestCase {
  public void testSimple() {
    Scalar result = HammingWindow.FUNCTION.apply(RealScalar.of(.2));
    Scalar expect = RealScalar.of(0.68455123656247599796); // checked with Mathematica
    Chop._12.requireClose(result, expect);
  }

  public void testOutside() {
    Scalar scalar = HammingWindow.FUNCTION.apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(HammingWindow.of(tensor), tensor.map(HammingWindow.FUNCTION));
  }

  public void testQuantityFail() {
    try {
      HammingWindow.FUNCTION.apply(Quantity.of(0, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      HammingWindow.FUNCTION.apply(Quantity.of(2, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
