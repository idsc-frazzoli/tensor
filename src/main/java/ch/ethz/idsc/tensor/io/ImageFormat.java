// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.img.ColorFormat;

/** The {@link Dimensions} of tensors that represent images are
 * <code>WIDTH x HEIGHT x 4</code>
 * 
 * <p>The 4 entries in the last dimension are RGBA.
 * 
 * <p>This convention is consistent with Java
 * {@link Graphics2D}, {@link BufferedImage}, ...
 * 
 * <p><code>Tensor::get(x, y)</code>
 * refers to the same pixel as
 * <code>BufferedImage::getRGB(x, y)</code>
 * 
 * <p>Consistent also with the screen size, for instance 1280 x 720.
 * 
 * <p>see also
 * <a href="https://reference.wolfram.com/language/ref/ImageData.html">ImageData</a> */
public enum ImageFormat {
  ;
  /** @param bufferedImage with dimensions [width x height]
   * @return tensor with dimensions [width x height x 4] */
  public static Tensor from(BufferedImage bufferedImage) {
    return Tensors.matrix((i, j) -> ColorFormat.toVector(bufferedImage.getRGB(i, j)), //
        bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  /** @param bufferedImage grayscale image with dimensions [width x height]
   * @return tensor with dimensions [width x height] */
  public static Tensor fromGrayscale(BufferedImage bufferedImage) {
    DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
    ByteBuffer byteBuffer = ByteBuffer.wrap(dataBufferByte.getData());
    return Transpose.of(Tensors.matrix((i, j) -> RealScalar.of(byteBuffer.get() & 0xff), //
        bufferedImage.getHeight(), bufferedImage.getWidth()));
  }

  /** image export works with PNG format.
   * 
   * <p>do not export to JPG, because the color channels are not compatible!
   * 
   * @param tensor
   * @return image of type BufferedImage.TYPE_BYTE_GRAY or BufferedImage.TYPE_INT_ARGB */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2)
      return toTYPE_BYTE_GRAY(tensor, dims);
    return toTYPE_INT_ARGB(tensor, dims);
  }

  // helper function
  private static BufferedImage toTYPE_BYTE_GRAY(Tensor tensor, List<Integer> dims) {
    BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_BYTE_GRAY);
    DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
    ByteBuffer byteBuffer = ByteBuffer.wrap(dataBufferByte.getData());
    Transpose.of(tensor).flatten(1) //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .forEach(number -> byteBuffer.put(number.byteValue()));
    return bufferedImage;
  }

  // helper function
  private static BufferedImage toTYPE_INT_ARGB(Tensor tensor, List<Integer> dims) {
    BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_INT_ARGB);
    // TODO use DataBufferByte
    Tensor res = TensorMap.of(vector -> RealScalar.of(ColorFormat.toInt(vector)), tensor, 2);
    int[] array = Primitives.toArrayInt(Transpose.of(res));
    bufferedImage.setRGB(0, 0, dims.get(0), dims.get(1), array, 0, dims.get(0));
    return bufferedImage;
  }
}
