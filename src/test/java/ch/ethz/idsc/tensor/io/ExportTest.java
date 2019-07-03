// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.img.MeanFilter;
import ch.ethz.idsc.tensor.pdf.BinomialDistribution;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class ExportTest extends TestCase {
  public void testCsv() throws IOException {
    File file = TestFile.withExtension("csv");
    Tensor tensor = Tensors.fromString("{{2, 3.123+3*I[V]}, {34.1231`32, 556, 3/456, -323/2}}");
    Export.of(file, tensor);
    assertEquals(tensor, Import.of(file));
    assertTrue(file.delete());
  }

  public void testCsvGz() throws IOException {
    File file = TestFile.withExtension("csv");
    Tensor tensor = Tensors.fromString("{{0, 2, 3.123+3*I[V]}, {34.1231`32, 556, 3/456, -323/2}}");
    Export.of(file, tensor);
    Tensor imported = Import.of(file);
    assertTrue(file.delete());
    assertEquals(tensor, imported);
  }

  public void testCsvGzLarge() throws IOException {
    File file = TestFile.withExtension("csv");
    Distribution distribution = BinomialDistribution.of(10, RealScalar.of(.3));
    Tensor tensor = RandomVariate.of(distribution, 60, 30);
    Export.of(file, tensor);
    Tensor imported = Import.of(file);
    assertTrue(file.delete());
    assertEquals(tensor, imported);
    assertTrue(ExactTensorQ.of(imported));
  }

  public void testPngColor() throws IOException {
    File file = TestFile.withExtension("png");
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11, 4);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    assertTrue(file.delete());
  }

  public void testPngGray() throws IOException {
    File file = TestFile.withExtension("png");
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    assertTrue(file.delete());
  }

  public void testJpgColor() throws IOException {
    File file = TestFile.withExtension("jpg");
    Tensor image = MeanFilter.of(RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11, 4), 2);
    image.set(Array.of(f -> RealScalar.of(255), 7, 11), Tensor.ALL, Tensor.ALL, 3);
    Export.of(file, image);
    Tensor diff = image.subtract(Import.of(file));
    Scalar total = diff.map(Abs.FUNCTION).flatten(-1).reduce(Tensor::add).get().Get();
    Scalar pixel = total.divide(RealScalar.of(4 * 77.0));
    assertTrue(Scalars.lessEquals(pixel, RealScalar.of(6)));
    assertTrue(file.delete());
  }

  public void testJpgGray() throws IOException {
    File file = TestFile.withExtension("jpg");
    Tensor image = MeanFilter.of(RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11), 4);
    Export.of(file, image);
    Tensor diff = image.subtract(Import.of(file));
    Scalar total = diff.map(Abs.FUNCTION).flatten(-1).reduce(Tensor::add).get().Get();
    Scalar pixel = total.divide(RealScalar.of(77.0));
    assertTrue(Scalars.lessEquals(pixel, RealScalar.of(5)));
    assertTrue(file.delete());
  }

  public void testBmpColor() throws IOException {
    File file = TestFile.withExtension("bmp");
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11, 4);
    image.set(Array.of(f -> RealScalar.of(255), 7, 11), Tensor.ALL, Tensor.ALL, 3);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    assertTrue(file.delete());
  }

  public void testBmpGray() throws IOException {
    File file = TestFile.withExtension("bmp");
    Tensor image = RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 7, 11);
    Export.of(file, image);
    assertEquals(image, Import.of(file));
    assertTrue(file.delete());
  }

  public void testMatlabM() throws IOException {
    File file = TestFile.withExtension("m");
    Tensor tensor = Tensors.fromString("{{2, 3.123+3*I, 34.1231}, {556, 3/456, -323/2}}");
    Export.of(file, tensor);
    assertTrue(file.delete());
  }

  public void testFailFile() {
    File file = new File("folder/does/not/exist/ethz.m");
    assertFalse(file.exists());
    try {
      Export.of(file, Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertFalse(file.exists());
  }

  public void testFailExtension() {
    File file = new File("ethz.idsc");
    assertFalse(file.exists());
    try {
      Export.of(file, Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertFalse(file.exists());
  }

  public void testBmpNull() throws IOException {
    File file = TestFile.withExtension("bmp");
    try {
      Export.of(file, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertFalse(file.exists());
  }

  public void testBmpGzNull() throws IOException {
    File file = TestFile.withExtension("bmp.gz");
    try {
      Export.of(file, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertFalse(file.exists());
  }

  public void testObjectNullFail() {
    File file = new File("tensorTestObjectNullFail.file");
    assertFalse(file.exists());
    try {
      Export.object(file, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
