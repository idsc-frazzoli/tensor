// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class GCDTest extends TestCase {
  public void testExamples() {
    assertEquals(GCD.of(RealScalar.of(+90), RealScalar.of(+60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(+90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(-90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(-90), RealScalar.of(+60)), RealScalar.of(30));
  }

  public void testZero() {
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(+60)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(+60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(-60)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(-60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(0)), RealScalar.of(0));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream().map(Scalar.class::cast).reduce(GCD::of).get();
    assertEquals(scalar.toString(), "28");
  }
}
