// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
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

  public void testTensor() throws Exception {
    File file = new File(getClass().getResource("/io/hilbert6x8.tensor").getFile());
    Tensor tensor = Import.of(file);
    assertEquals(tensor, HilbertMatrix.of(6, 8));
  }
}
