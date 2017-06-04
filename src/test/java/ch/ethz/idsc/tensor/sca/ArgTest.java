// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class ArgTest extends TestCase {
  public void testArg() {
    Scalar s = ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    assertEquals(Arg.of(s).toString(), "-3.066732805879026");
    assertEquals(Arg.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Arg.of(DoubleScalar.of(.2)), RealScalar.ZERO);
    assertEquals(Arg.of(DoubleScalar.of(-1)), DoubleScalar.of(Math.PI));
    assertEquals(Arg.of(RationalScalar.of(-1, 3)), DoubleScalar.of(Math.PI));
  }
}
