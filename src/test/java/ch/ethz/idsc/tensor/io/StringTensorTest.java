// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class StringTensorTest extends TestCase {
  public void testVector() {
    Tensor tensor = StringTensor.vector("IDSC", "ETH-Z", "ch");
    assertTrue(StringScalarQ.of(tensor.Get(0)));
    assertEquals(tensor.Get(0).toString(), "IDSC");
  }
}
