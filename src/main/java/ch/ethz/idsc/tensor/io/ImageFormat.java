// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.img.ColorFormat;

/** ImageFormat uses the data alignment of {@link BufferedImage}.
 * 
 * <p>The {@link Dimensions} of tensors that represent native images are
 * For grayscale:
 * <code>height x width</code>
 * 
 * For color (not supported yet):
 * <code>height x width x 4</code>
 * The 4 entries in the last dimension are RGBA.
 * 
 * <p><code>tensor.get(y, x)</code> refers to the same pixel as
 * <code>BufferedImage::getRGB(x, y)</code>
 * 
 * <p>see also
 * <a href="https://reference.wolfram.com/language/ref/ImageData.html">ImageData</a> */
public enum ImageFormat {
  ;
  /** encode image as tensor. {@link Dimensions} of output are
   * [height x width] for grayscale images of type BufferedImage.TYPE_BYTE_GRAY
   * [height x width x 4] for color images
   * 
   * @param bufferedImage
   * @return tensor encoding the color values of given bufferedImage */
  public static Tensor from(BufferedImage bufferedImage) {
    switch (bufferedImage.getType()) {
    case BufferedImage.TYPE_BYTE_GRAY:
      return fromGrayscale(bufferedImage);
    default:
      // TODO probably this can be done in a faster way
      return Tensors.matrix((y, x) -> ColorFormat.toVector(bufferedImage.getRGB(x, y)), //
          bufferedImage.getHeight(), bufferedImage.getWidth());
    }
  }

  /** @param tensor
   * @return image of type BufferedImage.TYPE_BYTE_GRAY or BufferedImage.TYPE_INT_ARGB */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2)
      return toTYPE_BYTE_GRAY(tensor, dims.get(1), dims.get(0));
    return toTYPE_INT_ARGB(tensor, dims.get(1), dims.get(0));
  }

  /** @param bufferedImage grayscale image with dimensions [width x height]
   * @return tensor with dimensions [height x width] */
  private static Tensor fromGrayscale(BufferedImage bufferedImage) {
    WritableRaster writableRaster = bufferedImage.getRaster();
    DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();
    ByteBuffer byteBuffer = ByteBuffer.wrap(dataBufferByte.getData());
    return Tensors.matrix((i, j) -> RealScalar.of(byteBuffer.get() & 0xff), //
        bufferedImage.getHeight(), bufferedImage.getWidth());
  }

  // helper function
  static BufferedImage toTYPE_BYTE_GRAY(Tensor tensor, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    WritableRaster writableRaster = bufferedImage.getRaster();
    DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();
    byte[] bytes = dataBufferByte.getData();
    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
    tensor.flatten(1) //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .forEach(number -> byteBuffer.put(number.byteValue()));
    return bufferedImage;
  }

  // fast extraction of color information to buffered image
  private static BufferedImage toTYPE_INT_ARGB(Tensor tensor, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int[] array = tensor.flatten(1).mapToInt(ColorFormat::toInt).toArray();
    bufferedImage.setRGB(0, 0, width, height, array, 0, width);
    return bufferedImage;
  }
}