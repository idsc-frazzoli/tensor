// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExportTest extends TestCase {
  public void testFail() {
    try {
      Export.of(new File("ajshgd.ueyghasfd"), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
