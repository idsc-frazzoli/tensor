// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class NestTest extends TestCase {
  public void testSimple() {
    Tensor actual = Nest.of( //
        scalar -> Power.of(scalar.add(RealScalar.ONE), RealScalar.of(2)), RealScalar.of(1), 3);
    assertEquals(RealScalar.of(676), actual);
  }

  public void testGamma() {
    Scalar expected = ComplexScalar.of(0.024484718696096586, -0.3838080212320521);
    Scalar actual = Nest.of(Gamma.FUNCTION, ComplexScalar.of(.3, .9), 3);
    assertTrue(Chop._08.close(expected, actual));
  }
}
