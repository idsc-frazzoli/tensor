// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class LogisticSigmoidTest extends TestCase {
  public void testBasic() {
    assertEquals(LogisticSigmoid.function.apply(RealScalar.ZERO).toString(), "0.5");
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(0.)).toString(), "0.5");
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(1e3)).toString(), "1.0");
    assertEquals(LogisticSigmoid.function.apply(RealScalar.of(-1e3)).toString(), "0.0");
    Scalar big = LogisticSigmoid.function.apply(RealScalar.of(0.5));
    assertTrue(big.toString().startsWith("0.622459")); // from mathematica
  }
}
