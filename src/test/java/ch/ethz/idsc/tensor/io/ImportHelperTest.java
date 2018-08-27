// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.sca.Unitize;
import junit.framework.TestCase;

public class ImportHelperTest extends TestCase {
  public void testGif() throws Exception {
    String string = "/io/rgba7x3.gif"; // file consist of a single line break character
    File file = new File(getClass().getResource(string).getFile());
    Tensor tensor = Import.of(file);
    assertEquals(Dimensions.of(tensor), Arrays.asList(3, 7, 4));
    assertEquals(tensor.get(0, 0), Tensors.vector(0, 0, 0, 255));
    assertEquals(tensor.Get(0, 1, 3), RealScalar.ZERO);
    assertEquals(tensor.get(0, 3), Tensors.vector(255, 255, 255, 255));
    assertEquals(tensor.get(2, 5), Tensors.vector(145, 74, 198, 255));
    Tensor tensor2 = tensor.get(Tensor.ALL, Tensor.ALL, 3);
    Tensor units = Unitize.of(tensor2);
    assertEquals(units.flatten(-1).reduce(Tensor::add).get().Get(), RealScalar.of(9));
  }

  public void testExtensionMFail() {
    InputStream inputStream = new ByteArrayInputStream(new byte[128]);
    try {
      ImportHelper.of(new Filename("some.m"), inputStream);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSwitch() {
    Extension extension = null;
    try {
      extension = Extension.valueOf("asd");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      switch (extension) {
      default:
      }
      assertTrue(false);
    } catch (NullPointerException exception) {
      // ---
    }
  }
}
