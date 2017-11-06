// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.utl.UserHome;
import junit.framework.TestCase;

public class ExportTest extends TestCase {
  private static void static_check(File file) throws ClassNotFoundException, DataFormatException, IOException {
    assertFalse(file.isFile());
    Tensor tensor = Tensors.fromString("{{2,3.123+3*I[V]},{34.1231`32,556,3/456,-323/2}}");
    Export.of(file, tensor);
    assertEquals(tensor, Import.of(file));
    file.delete();
  }

  public void testCsvAndTensor() throws IOException, ClassNotFoundException, DataFormatException {
    static_check(UserHome.file("tensorLib_ExportTest.csv"));
    static_check(UserHome.file("tensorLib_ExportTest.tensor"));
  }

  public void testPngColor() throws ClassNotFoundException, DataFormatException, IOException {
    File file = UserHome.file("tensorLib_ExportTest.png");
    assertFalse(file.isFile());
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11, 4);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    file.delete();
  }

  public void testPngGray() throws ClassNotFoundException, DataFormatException, IOException {
    File file = UserHome.file("tensorLib_ExportTest.png");
    assertFalse(file.isFile());
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    file.delete();
  }

  public void testMatlabM() throws ClassNotFoundException, DataFormatException, IOException {
    File file = UserHome.file("tensorLib_ExportTest.m");
    assertFalse(file.isFile());
    Tensor tensor = Tensors.fromString("{{2,3.123+3*I,34.1231},{556,3/456,-323/2}}");
    Export.of(file, tensor);
    file.delete();
  }

  public void testFail() {
    try {
      Export.of(new File("ajshgd.ueyghasfd"), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
