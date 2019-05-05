// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class ParamContainerTest extends TestCase {
  public void testSimple() {
    ParamContainer paramContainer = ParamContainer.INSTANCE;
    assertTrue(paramContainer.maxTor instanceof Quantity);
    assertEquals(paramContainer.shape.length(), 4);
  }

  public void testFile() {
    ParamContainerFile paramContainerFile = new ParamContainerFile();
    paramContainerFile.file = HomeDirectory.file("file.txt");
    TensorProperties tensorProperties = TensorProperties.wrap(paramContainerFile);
    Properties properties = tensorProperties.get();
    ParamContainerFile copy = new ParamContainerFile();
    TensorProperties wrap = TensorProperties.wrap(copy);
    wrap.set(properties);
    assertEquals(copy.file, paramContainerFile.file);
  }

  public void testExportImport() throws IOException {
    ParamContainerFile paramContainerFile = new ParamContainerFile();
    paramContainerFile.text = "abc";
    paramContainerFile.tensor = Tensors.vector(1, 2, 3);
    paramContainerFile.file = HomeDirectory.file("file.txt");
    TensorProperties tensorProperties = TensorProperties.wrap(paramContainerFile);
    File storage = TestFile.withExtension("properties");
    tensorProperties.save(storage);
    ParamContainerFile loaded = TensorProperties.wrap(new ParamContainerFile()).tryLoad(storage);
    assertEquals(loaded.file, paramContainerFile.file);
    assertEquals(loaded.tensor, paramContainerFile.tensor);
    assertEquals(loaded.text, paramContainerFile.text);
    storage.delete();
  }
}
