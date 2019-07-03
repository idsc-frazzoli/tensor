// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class ObjectFormatTest extends TestCase {
  public void testSome() throws Exception {
    Tensor inp = Tensors.fromString("{1, {2, 3, {4.3}}, 1}");
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }

  public void testNull() throws Exception {
    final Object put = null;
    byte[] bytes = ObjectFormat.of(put);
    Object get = ObjectFormat.parse(bytes);
    assertEquals(put, get);
    assertNull(get);
  }

  public void testExportImportObject() throws IOException, ClassNotFoundException, DataFormatException {
    Tensor tensor = HilbertMatrix.of(3, 4);
    File file = TestFile.withExtension("random");
    Export.object(file, tensor);
    assertTrue(file.isFile());
    assertEquals(Import.object(file), tensor);
    assertTrue(file.delete());
  }
}
