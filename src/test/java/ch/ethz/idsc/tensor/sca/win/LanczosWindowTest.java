// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class LanczosWindowTest extends TestCase {
  public void testSimple() {
    Chop._15.requireClose(LanczosWindow.FUNCTION.apply(RealScalar.of(0.125)), Sqrt.of(RealScalar.of(2)).divide(Pi.HALF));
    Chop._15.requireClose(LanczosWindow.FUNCTION.apply(RealScalar.of(0.25)), Pi.HALF.reciprocal());
    Chop._15.requireClose(LanczosWindow.FUNCTION.apply(RealScalar.of(0.5)), RealScalar.ZERO);
    assertTrue(Scalars.isZero(LanczosWindow.FUNCTION.apply(RealScalar.of(0.51))));
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(LanczosWindow.of(tensor), tensor.map(LanczosWindow.FUNCTION));
  }
}
