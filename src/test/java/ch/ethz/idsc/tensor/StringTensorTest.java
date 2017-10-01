// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class StringTensorTest extends TestCase {
  public void testSimple() {
    Tensor tensor = StringTensor.vector("IDSC", "ETH-Z", "ch");
    assertTrue(tensor.Get(0) instanceof StringScalar);
    assertEquals(tensor.Get(0).toString(), "IDSC");
  }
}
