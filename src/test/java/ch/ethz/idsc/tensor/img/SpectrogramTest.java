// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Arrays;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.SpectrogramArray;
import ch.ethz.idsc.tensor.sca.Abs;
import junit.framework.TestCase;

public class SpectrogramTest extends TestCase {
  public void testDefault() {
    Tensor vector = Tensor.of(IntStream.range(0, 2000) //
        .mapToDouble(i -> Math.cos(i * 0.25 + (i / 20.0) * (i / 20.0))) //
        .mapToObj(RealScalar::of));
    Tensor image = Spectrogram.of(vector);
    ImageFormat.of(image);
    assertEquals(Dimensions.of(image), Arrays.asList(32, 93, 4));
    assertEquals(Dimensions.of(Spectrogram.array(vector)), Arrays.asList(32, 93));
    Tensor tensor = SpectrogramArray.of(vector).map(Abs.FUNCTION);
    assertEquals(Dimensions.of(tensor), Arrays.asList(93, 64));
  }

  public void testScalarFail() {
    try {
      Spectrogram.of(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Spectrogram.of(HilbertMatrix.of(32));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
