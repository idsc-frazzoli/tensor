// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExportHelperTest extends TestCase {
  public void testFileExtensionFail() {
    OutputStream outputStream = new ByteArrayOutputStream(512);
    try {
      ExportHelper.of(Extension.VECTOR, Tensors.empty(), outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testGzFail() {
    OutputStream outputStream = new ByteArrayOutputStream(512);
    try {
      ExportHelper.of(Extension.GZ, Tensors.empty(), outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
