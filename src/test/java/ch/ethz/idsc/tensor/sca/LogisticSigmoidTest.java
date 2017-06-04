// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class LogisticSigmoidTest extends TestCase {
  public void testBasic() {
    assertEquals(LogisticSigmoid.function.apply(RealScalar.ZERO), RealScalar.of(0.5));
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(0.)), RealScalar.of(0.5));
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(1e3)), RealScalar.ONE);
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(-1e3)), RealScalar.ZERO);
  }

  public void testSimple() {
    Scalar big = LogisticSigmoid.function.apply(RealScalar.of(0.5));
    assertTrue(big.toString().startsWith("0.622459")); // from mathematica
  }
}
