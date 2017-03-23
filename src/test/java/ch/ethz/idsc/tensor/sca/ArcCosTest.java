// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcCosTest extends TestCase {
  public void testReal() {
    assertEquals(ArcCos.function.apply(Scalars.fromString("1")), RealScalar.of(Math.acos(1)));
    assertEquals(ArcCos.function.apply(Scalars.fromString("-1")), RealScalar.of(Math.acos(-1)));
  }
}
