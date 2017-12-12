// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class SoftplusTest extends TestCase {
  public void testSimple() {
    Scalar s = Softplus.FUNCTION.apply(RealScalar.ZERO);
    assertEquals(s, RealScalar.of(0.6931471805599453));
  }

  public void testOuter() {
    assertEquals(Softplus.FUNCTION.apply(RealScalar.of(+1000000)), RealScalar.of(1000000));
    assertEquals(Softplus.FUNCTION.apply(RealScalar.of(-1000000)), RealScalar.of(0));
  }
}
