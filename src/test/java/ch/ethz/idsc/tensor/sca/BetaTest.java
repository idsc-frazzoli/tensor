// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class BetaTest extends TestCase {
  public void testExact() {
    Scalar beta = Beta.of(5, 4);
    Scalar exact = RationalScalar.of(1, 280);
    assertTrue(Chop._14.close(beta, exact));
  }

  public void testNumeric() {
    Scalar beta = Beta.of(2.3, 3.2);
    Scalar exact = RealScalar.of(0.05402979174835722);
    assertTrue(Chop._14.close(beta, exact));
  }
}
