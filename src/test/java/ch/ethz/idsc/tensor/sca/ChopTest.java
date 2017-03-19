// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class ChopTest extends TestCase {
  public void testChop() {
    Tensor v = Tensors.vectorDouble(1e-10, 1e-14, 1e-16);
    Tensor c = v.map(Chop.function);
    assertFalse(c.get(0).equals(ZeroScalar.get()));
    assertTrue(c.get(1).equals(ZeroScalar.get()));
    assertTrue(c.get(2).equals(ZeroScalar.get()));
  }
}
