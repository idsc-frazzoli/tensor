// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class ExpTest extends TestCase {
  public void testEuler() {
    Scalar emi = Exp.function.apply(ComplexScalar.of(0, -Math.PI));
    Scalar tru = RealScalar.of(-1);
    assertEquals(Chop.of(emi.subtract(tru)), ZeroScalar.get());
  }
}
