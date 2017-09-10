// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class GetTest extends TestCase {
  public void testSimple() throws IOException {
    File file = new File(getClass().getResource("/io/basic.mathematica").getFile());
    Tensor tensor = Get.of(file);
    assertTrue(Objects.nonNull(tensor));
    assertFalse(ScalarQ.of(tensor));
    assertEquals(tensor.length(), 13);
  }
}
