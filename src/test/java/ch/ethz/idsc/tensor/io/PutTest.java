// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class PutTest extends TestCase {
  public void testUnstructured() throws IOException {
    File file = TestFile.withExtension("put");
    Tensor tensor = Tensors.fromString("{{2,3.123+3*I,34.1231},{556,3/456,-323/2,{3,8.45`}}}");
    Put.of(file, tensor.unmodifiable());
    Tensor readin = Get.of(file);
    assertTrue(file.delete());
    assertFalse(file.exists());
    assertEquals(tensor, readin);
  }

  public void testNullFail() {
    File file = TestFile.withExtension("put");
    try {
      Put.of(file, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
