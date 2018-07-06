// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LCMTest extends TestCase {
  public void testExamples() {
    assertEquals(LCM.of(RealScalar.of(+123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(+123 * 5), RealScalar.of(345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(-123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(+123), RealScalar.of(-345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(-123), RealScalar.of(-345)), RealScalar.of(14145));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream().map(Scalar.class::cast).reduce(LCM::of).get();
    assertEquals(scalar.toString(), "145600");
  }
}
