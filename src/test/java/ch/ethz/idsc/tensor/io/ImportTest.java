// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ImportTest extends TestCase {
  public void testCsv() throws Exception {
    File file = new File(getClass().getResource("/io/libreoffice_calc.csv").getFile());
    Tensor table = Import.of(file);
    assertEquals(Dimensions.of(table), Arrays.asList(4, 2));
  }

  public void testPng() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
  }

  public void testJpg() throws Exception {
    File file = new File(getClass().getResource("/io/rgb15x33.jpg").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
    assertEquals(Tensors.vector(180, 46, 47, 255), tensor.get(21, 3)); // verified with gimp
  }

  public void testObject() throws ClassNotFoundException, DataFormatException, IOException {
    // Export.object(UserHome.file("string.object"), "tensorlib.importtest");
    File file = new File(getClass().getResource("/io/string.object").getFile());
    String string = Import.object(file);
    assertEquals(string, "tensorlib.importtest");
  }

  public void testUnknownFail() {
    File file = new File(getClass().getResource("/io/extension.unknown").getFile());
    try {
      Import.of(file);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
