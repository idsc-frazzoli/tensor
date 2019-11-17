// code by jph
package ch.ethz.idsc.tensor.sca.win;

import java.io.IOException;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GaussianWindowTest extends TestCase {
  public void testSimple() {
    Scalar apply = GaussianWindow.FUNCTION.apply(RealScalar.of(0.2));
    Scalar exact = RealScalar.of(0.80073740291680804078);
    Chop._10.requireClose(apply, exact);
  }

  public void testOutside() throws ClassNotFoundException, IOException {
    Scalar scalar = Serialization.copy(GaussianWindow.FUNCTION).apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(GaussianWindow.of(tensor), tensor.map(GaussianWindow.FUNCTION));
  }

  public void testCustom() throws ClassNotFoundException, IOException {
    GaussianWindow copy = Serialization.copy(new GaussianWindow(RationalScalar.of(2, 10)));
    Scalar apply = copy.apply(RealScalar.of(0.4));
    Scalar exact = RealScalar.of(0.13533528323661262);
    Chop._10.requireClose(apply, exact);
  }
}
