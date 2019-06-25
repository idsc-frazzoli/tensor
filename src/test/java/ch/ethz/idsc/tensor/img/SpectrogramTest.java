// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Arrays;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.SpectrogramArray;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Abs;
import junit.framework.TestCase;

public class SpectrogramTest extends TestCase {
  public void testDefault() {
    Tensor vector = Tensor.of(IntStream.range(0, 2000) //
        .mapToDouble(i -> Math.cos(i * 0.25 + (i / 20.0) * (i / 20.0))) //
        .mapToObj(RealScalar::of));
    Tensor image = Spectrogram.of(vector, ColorDataGradients.VISIBLESPECTRUM);
    ImageFormat.of(image);
    assertEquals(Dimensions.of(image), Arrays.asList(32, 93, 4));
    assertEquals(Dimensions.of(Spectrogram.array(vector)), Arrays.asList(32, 93));
    Tensor tensor = SpectrogramArray.of(vector).map(Abs.FUNCTION);
    assertEquals(Dimensions.of(tensor), Arrays.asList(93, 64));
  }

  public void testQuantity() {
    Tensor signal = Tensors.vector(1, 2, 1, 4, 3, 2, 3, 4, 3, 4);
    Tensor vector = signal.map(s -> Quantity.of(s, "m"));
    Tensor array1 = Spectrogram.of(signal, ColorDataGradients.VISIBLESPECTRUM);
    Tensor array2 = Spectrogram.of(vector, ColorDataGradients.VISIBLESPECTRUM);
    assertEquals(array1, array2);
  }

  public void testNullFail() {
    try {
      Spectrogram.array(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Spectrogram.of(RealScalar.ONE, ColorDataGradients.VISIBLESPECTRUM);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Spectrogram.of(HilbertMatrix.of(32), ColorDataGradients.VISIBLESPECTRUM);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
