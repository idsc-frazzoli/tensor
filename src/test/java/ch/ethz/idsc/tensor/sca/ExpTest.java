// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class ExpTest extends TestCase {
  public void testEuler() {
    Scalar emi = Exp.FUNCTION.apply(ComplexScalar.of(0, -Math.PI));
    Scalar tru = RealScalar.of(-1);
    assertTrue(Chop._12.close(emi, tru));
  }

  public void testExpZero() {
    assertEquals(Exp.of(RealScalar.ZERO), RealScalar.ONE);
    assertEquals(Log.of(RealScalar.ONE), RealScalar.ZERO);
  }
}
