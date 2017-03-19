// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class KroneckerDeltaTest extends TestCase {
  public void testKroneckerDelta() {
    final Scalar one = RationalScalar.of(1, 1);
    assertEquals(KroneckerDelta.of(), one);
    assertEquals(KroneckerDelta.of(1), one);
    assertEquals(KroneckerDelta.of(1, 2), ZeroScalar.get());
    assertEquals(KroneckerDelta.of(3, 3, 3), one);
    assertEquals(KroneckerDelta.of(3, 3, 1), ZeroScalar.get());
  }
}
