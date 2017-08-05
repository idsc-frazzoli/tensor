// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** {@link NativeImageFormat} is a helper class for {@link ImageFormat}.
 * 
 * <p>The {@link Dimensions} of tensors that represent native images are
 * <code>HEIGHT x WIDTH x 4</code>
 * 
 * <p>The 4 entries in the last dimension are RGBA.
 * 
 * <p><code>tensor.get(y, x)</code> refers to the same pixel as
 * <code>BufferedImage::getRGB(x, y)</code>
 * 
 * <p>see also
 * <a href="https://reference.wolfram.com/language/ref/ImageData.html">ImageData</a> */
enum NativeImageFormat {
  ;
  /** @param bufferedImage grayscale image with dimensions [width x height]
   * @return tensor with dimensions [height x width] */
  public static Tensor fromGrayscale(BufferedImage bufferedImage) {
    DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
    ByteBuffer byteBuffer = ByteBuffer.wrap(dataBufferByte.getData());
    return Tensors.matrix((i, j) -> RealScalar.of(byteBuffer.get() & 0xff), //
        bufferedImage.getHeight(), bufferedImage.getWidth());
  }

  /** @param tensor
   * @return image of type BufferedImage.TYPE_BYTE_GRAY or BufferedImage.TYPE_INT_ARGB */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2)
      return toTYPE_BYTE_GRAY(tensor, dims.get(1), dims.get(0));
    throw TensorRuntimeException.of(tensor);
  }

  // helper function
  /* package */ static BufferedImage toTYPE_BYTE_GRAY(Tensor tensor, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
    ByteBuffer byteBuffer = ByteBuffer.wrap(dataBufferByte.getData());
    tensor.flatten(1) //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .forEach(number -> byteBuffer.put(number.byteValue()));
    return bufferedImage;
  }
}
