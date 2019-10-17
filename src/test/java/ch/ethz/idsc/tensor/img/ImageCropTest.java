// code by jph
package ch.ethz.idsc.tensor.img;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import junit.framework.TestCase;

public class ImageCropTest extends TestCase {
  public void testSerialization() throws ClassNotFoundException, IOException {
    TensorUnaryOperator tensorUnaryOperator = //
        ImageCrop.color(Tensors.vector(255, 255, 255, 255));
    Serialization.copy(tensorUnaryOperator);
  }

  public void testGrayscale() {
    TensorUnaryOperator tensorUnaryOperator = ImageCrop.color(RealScalar.ZERO);
    Tensor image = Tensors.fromString("{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}");
    Tensor result = tensorUnaryOperator.apply(image);
    assertEquals(result, Tensors.fromString("{{1}}"));
  }

  public void testColor() {
    Tensor image = Tensors.fromString("{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}");
    image = ArrayPlot.of(image, ColorDataGradients.CLASSIC);
    TensorUnaryOperator tensorUnaryOperator = ImageCrop.color(image.get(0, 0));
    Tensor result = tensorUnaryOperator.apply(image);
    assertEquals(result, Tensors.fromString("{{{255, 237, 237, 255}}}"));
  }

  public void testNoCropGrayscale() {
    TensorUnaryOperator imagecrop = ImageCrop.color(RealScalar.of(123));
    Tensor tensor = ResourceData.of("/io/image/gray15x9.png");
    Tensor result = imagecrop.apply(tensor);
    assertEquals(tensor, result);
  }

  public void testNoCropRgba() {
    TensorUnaryOperator imagecrop = ImageCrop.color(Tensors.vector(1, 2, 3, 4));
    Tensor tensor = ResourceData.of("/io/image/rgba15x33.png");
    Tensor result = imagecrop.apply(tensor);
    assertEquals(tensor, result);
  }

  public void testVectorFail() {
    TensorUnaryOperator tensorUnaryOperator = ImageCrop.color(RealScalar.ONE);
    try {
      tensorUnaryOperator.apply(Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
