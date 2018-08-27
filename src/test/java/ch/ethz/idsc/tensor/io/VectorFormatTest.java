// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class VectorFormatTest extends TestCase {
  public void testVector() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(128);
    Tensor tensor = Tensors.fromString("{2,3,4.125,\"abc\",4/3[m*s^-1],xyz\",3+I/7,ethz}");
    ExportHelper.of(Extension.VECTOR, tensor, outputStream);
    byte[] array = outputStream.toByteArray(); // 44 bytes used
    InputStream inputStream = new ByteArrayInputStream(array);
    Tensor result = ImportHelper.of(new Filename("some.vEcToR"), inputStream);
    assertEquals(tensor, result);
  }

  public void testVectorScalarFail() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(128);
    Tensor tensor = RealScalar.ONE;
    try {
      ExportHelper.of(Extension.VECTOR, tensor, outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVectorMatrixFail() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(128);
    Tensor tensor = IdentityMatrix.of(3);
    try {
      ExportHelper.of(Extension.VECTOR, tensor, outputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
