// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.red.KroneckerDelta;
import junit.framework.TestCase;

public class KroneckerDeltaTest extends TestCase {
  public void testKroneckerDelta() {
    final Scalar one = RealScalar.ONE;
    assertEquals(KroneckerDelta.of(), one);
    assertEquals(KroneckerDelta.of(1), one);
    assertEquals(KroneckerDelta.of(1, 2), ZeroScalar.get());
    assertEquals(KroneckerDelta.of(3, 3, 3), one);
    assertEquals(KroneckerDelta.of(3, 3, 1), ZeroScalar.get());
  }
}
