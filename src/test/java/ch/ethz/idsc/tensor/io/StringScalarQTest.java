// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class StringScalarQTest extends TestCase {
  public void testOf() {
    assertFalse(StringScalarQ.of(RealScalar.ZERO));
    assertFalse(StringScalarQ.of(Tensors.fromString("{hello}")));
    assertTrue(StringScalarQ.of(StringScalar.of("world")));
  }

  public void testAny() {
    assertTrue(StringScalarQ.any(Tensors.of(RealScalar.ONE, StringScalar.of("world"))));
    assertFalse(StringScalarQ.any(Tensors.vector(1, 2, 3, 4)));
    assertFalse(StringScalarQ.any(Tensors.empty()));
  }
}
