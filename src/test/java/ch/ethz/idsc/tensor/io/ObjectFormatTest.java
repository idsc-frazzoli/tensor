// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.utl.UserHome;
import junit.framework.TestCase;

public class ObjectFormatTest extends TestCase {
  public void testSome() throws Exception {
    Tensor inp = Tensors.fromString("{1,{2,3,{4.3}},1}");
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }

  public void testNull() throws Exception {
    Tensor inp = null;
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }

  public void testExportImportObject() throws IOException, ClassNotFoundException, DataFormatException {
    Tensor tensor = HilbertMatrix.of(3, 4);
    File file = UserHome.file("test" + ObjectFormatTest.class.getSimpleName() + ".random");
    assertFalse(file.exists());
    Export.object(file, tensor);
    assertTrue(file.exists());
    assertEquals(Import.object(file), tensor);
    file.delete();
  }
}
