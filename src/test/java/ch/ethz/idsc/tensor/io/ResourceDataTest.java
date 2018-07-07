// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import junit.framework.TestCase;

public class ResourceDataTest extends TestCase {
  private static void _checkColorscheme(Interpolation interpolation) {
    try {
      interpolation.get(Tensors.vector(256));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testColorschemeClassic() throws IOException {
    Tensor tensor = ResourceData.of("/colorscheme/classic.csv");
    assertNotNull(tensor);
    assertEquals(Dimensions.of(tensor), Arrays.asList(256, 4));
    Interpolation interpolation = LinearInterpolation.of(tensor);
    assertEquals(interpolation.get(Tensors.vector(255)), Tensors.vector(255, 237, 237, 255));
    _checkColorscheme(interpolation);
  }

  public void testHue() throws IOException {
    Tensor tensor = ResourceData.of("/colorscheme/hue.csv");
    assertNotNull(tensor);
    assertEquals(Dimensions.of(tensor), Arrays.asList(7, 4));
    Interpolation interpolation = LinearInterpolation.of(tensor);
    assertEquals(interpolation.get(Tensors.vector(0)), Tensors.vector(255, 0, 0, 255));
    _checkColorscheme(interpolation);
  }

  public void testPrimes() throws IOException {
    Tensor primes = ResourceData.of("/number/primes.vector");
    assertNotNull(primes);
    List<Integer> dimensions = Dimensions.of(primes);
    assertEquals(dimensions.size(), 1);
    assertEquals(primes.Get(5), Scalars.fromString("13"));
  }

  public void testJpg() throws IOException {
    Tensor image = ResourceData.of("/io/rgb15x33.jpg");
    assertEquals(Dimensions.of(image), Arrays.asList(33, 15, 4));
  }

  public void testBmp() throws IOException {
    Tensor image = ResourceData.of("/io/rgb7x11.bmp");
    assertEquals(Dimensions.of(image), Arrays.asList(11, 7, 4));
    assertEquals(image.get(10, 4), Tensors.vector(0, 7, 95, 255));
  }

  public void testFailNull() {
    assertNull(ResourceData.of("/number/exists.fail"));
    assertNull(ResourceData.of("/number/exists.fail.bmp"));
  }

  public void testObjectNull() {
    assertNull(ResourceData.object("/number/exists.fail"));
  }

  public void testPropertiesFailNull() {
    assertNull(ResourceData.properties("/number/exists.properties"));
  }

  public void testUnknownExtension() {
    assertNull(ResourceData.of("/io/extension.unknown"));
  }

  public void testCorruptContent() {
    assertNull(ResourceData.of("/io/corrupt.png"));
  }
}
