// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MatFileFormatTest extends TestCase {
  public void testGet() {
    try {
      MatFileFormat.parse(new byte[] { 1, 2, 3 });
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPut() {
    try {
      MatFileFormat.of(Tensors.vector(2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
