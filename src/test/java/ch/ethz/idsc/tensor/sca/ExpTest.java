// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class ExpTest extends TestCase {
  public void testEuler() {
    Scalar emi = Exp.function.apply(ComplexScalar.of(0, -Math.PI));
    Scalar tru = RealScalar.of(-1);
    assertTrue(Chop.isZeros(emi.subtract(tru)));
  }

  public void testExpZero() {
    assertTrue(Exp.of(ZeroScalar.get()) instanceof RationalScalar);
    assertTrue(Log.of(RealScalar.ONE) instanceof ZeroScalar);
  }
}
