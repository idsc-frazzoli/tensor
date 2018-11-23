// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class NuttallWindowTest extends TestCase {
  public void testZero() {
    ScalarUnaryOperator scalarUnaryOperator = NuttallWindow.FUNCTION;
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.ZERO);
    assertEquals(scalar, RealScalar.ONE);
  }

  public void testOutside() {
    Scalar scalar = NuttallWindow.FUNCTION.apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
  }
}
