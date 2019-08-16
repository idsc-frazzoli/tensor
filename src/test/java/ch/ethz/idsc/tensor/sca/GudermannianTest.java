// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class GudermannianTest extends TestCase {
  public void testSimple() {
    Scalar scalar = Gudermannian.FUNCTION.apply(RationalScalar.HALF);
    Chop._12.requireClose(scalar, RealScalar.of(0.48038107913372944860)); // mathematica
  }
}
