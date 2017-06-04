// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ChopTest extends TestCase {
  public void testChop() {
    Tensor v = Tensors.vectorDouble(1e-10, 1e-14, 1e-16);
    Tensor c = v.map(Chop.function);
    assertFalse(c.get(0).equals(RealScalar.ZERO));
    assertTrue(c.get(1).equals(RealScalar.ZERO));
    assertTrue(c.get(2).equals(RealScalar.ZERO));
  }

  public void testExclusive() {
    assertFalse(Chop.isZeros(RealScalar.of(Chop.THRESHOLD)));
  }
}
