// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class CoshTest extends TestCase {
  public void testReal() {
    Scalar c = Cosh.FUNCTION.apply(RealScalar.of(2));
    Scalar s = DoubleScalar.of(Math.cosh(2));
    Scalar t = Cosh.of(RealScalar.of(2));
    assertEquals(c, s);
    assertEquals(c, t);
  }

  public void testComplex() {
    Scalar c = Cosh.FUNCTION.apply(ComplexScalar.of(2, 3.));
    // -3.72455 + 0.511823 I
    Scalar s = Scalars.fromString("-3.7245455049153224+0.5118225699873846*I");
    assertEquals(c, s);
  }
}
