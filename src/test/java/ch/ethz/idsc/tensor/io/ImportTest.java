// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.utl.UserHome;
import junit.framework.TestCase;

public class ImportTest extends TestCase {
  public void testCsv() throws Exception {
    String string = "/io/libreoffice_calc.csv";
    File file = new File(getClass().getResource(string).getFile());
    Tensor table = Import.of(file);
    assertEquals(Dimensions.of(table), Arrays.asList(4, 2));
    assertEquals(ResourceData.of(string), table);
  }

  public void testCsvEmpty() throws Exception {
    String string = "/io/empty.csv"; // file has byte length 0
    File file = new File(getClass().getResource(string).getFile());
    assertTrue(Tensors.isEmpty(Import.of(file)));
    assertTrue(Tensors.isEmpty(ResourceData.of(string)));
  }

  public void testCsvEmptyLine() throws Exception {
    String string = "/io/emptyline.csv"; // file consist of a single line break character
    File file = new File(getClass().getResource(string).getFile());
    Tensor expected = Tensors.fromString("{{}}").unmodifiable();
    assertEquals(Import.of(file), expected);
    assertEquals(ResourceData.of(string), expected);
  }

  public void testCsvFail() throws Exception {
    File file = new File("/io/doesnotexist.csv");
    try {
      Import.of(file);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCsvGz() throws Exception {
    String string = "/io/mathematica23.csv.gz";
    File file = new File(getClass().getResource(string).getFile());
    Tensor table = Import.of(file);
    assertEquals(table, Tensors.fromString("{{123/875+I, 9.3}, {-9, 5/8123123123123123, 1010101}}"));
  }

  public void testCsvClosed() throws IOException, ClassNotFoundException, DataFormatException {
    File file = UserHome.file("tensorTest" + ImportTest.class.getSimpleName() + ".csv");
    assertFalse(file.exists());
    Export.of(file, Tensors.fromString("{{1, 2}, {3, 4}}"));
    assertTrue(file.exists());
    assertTrue(8 <= file.length());
    Import.of(file);
    boolean deleted = file.delete();
    assertTrue(deleted);
  }

  public void testImageClose() throws Exception {
    Tensor tensor = Tensors.fromString("{{1, 2}, {3, 4}}");
    File file = UserHome.file("tensorTest" + ImportTest.class.getSimpleName() + ".png");
    Export.of(file, tensor);
    assertTrue(file.exists());
    Tensor image = Import.of(file);
    assertEquals(tensor, image);
    file.delete();
    assertFalse(file.exists());
  }

  public void testFolderCsvClosed() throws IOException, ClassNotFoundException, DataFormatException {
    File dir = UserHome.file("tensorTest" + System.currentTimeMillis());
    assertFalse(dir.exists());
    dir.mkdir();
    assertTrue(dir.isDirectory());
    File file = new File(dir, "tensorTest" + ImportTest.class.getSimpleName() + ".csv");
    assertFalse(file.exists());
    Export.of(file, Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}}"));
    assertTrue(file.exists());
    assertTrue(12 <= file.length());
    Tensor table = Import.of(file);
    assertEquals(Dimensions.of(table), Arrays.asList(3, 2));
    assertTrue(file.delete());
    assertTrue(dir.delete());
  }

  public void testPng() throws Exception {
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
  }

  public void testPngClose() throws Exception {
    Tensor tensor = ResourceData.of("/io/rgba15x33.png");
    assertEquals(Dimensions.of(tensor), Arrays.asList(33, 15, 4));
    File file = UserHome.file("tensorTest" + ImportTest.class.getSimpleName() + ".png");
    Export.of(file, tensor);
    assertTrue(file.exists());
    Import.of(file);
    file.delete();
    assertFalse(file.exists());
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

  public void testTensor() throws Exception {
    File file = UserHome.file("asdfghjk.tensortest");
    assertFalse(file.exists());
    Export.object(file, Tensors.vector(1, 2, 3, 4));
    Tensor vector = Import.object(file);
    assertEquals(vector, Tensors.vector(1, 2, 3, 4));
    file.delete();
  }
}
