// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GaussianWindowTest extends TestCase {
  public void testSimple() {
    Scalar apply = GaussianWindow.FUNCTION.apply(RealScalar.of(0.2));
    Scalar exact = RealScalar.of(0.80073740291680804078);
    assertTrue(Chop._10.close(apply, exact));
  }

  public void testOutside() {
    Scalar scalar = GaussianWindow.FUNCTION.apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(GaussianWindow.of(tensor), tensor.map(GaussianWindow.FUNCTION));
  }
}
