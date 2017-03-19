// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MeanTest extends TestCase {
  public void testSome() {
    assertEquals(Mean.of(Tensors.vectorInt(3, 5)), RealScalar.of(4));
    assertEquals(Mean.of(Tensors.vectorDouble(3., 5., 0., 0.)), RealScalar.of(2));
  }
}
