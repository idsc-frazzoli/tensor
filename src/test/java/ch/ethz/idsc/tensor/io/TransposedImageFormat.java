// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.img.ColorFormat;

/** The {@link Dimensions} of tensors that represent color images are
 * <code>width x height x 4</code>
 * 
 * <p>The 4 entries in the last dimension are RGBA.
 * 
 * The {@link Dimensions} of tensors that represent gray scale images are
 * <code>width x height</code>
 * 
 * <p>This convention is consistent with Java
 * {@link Graphics2D}, {@link BufferedImage}, ...
 * 
 * <p><code>tensor.get(x, y)</code> refers to the same pixel as
 * <code>BufferedImage::getRGB(x, y)</code>
 * 
 * <p>Consistent also with the screen size, for instance 1280 x 720.
 * 
 * <p>see also
 * <a href="https://reference.wolfram.com/language/ref/ImageData.html">ImageData</a> */
/* package */ enum TransposedImageFormat {
  ;
  /** @param bufferedImage with dimensions [width x height]
   * @return tensor with dimensions [width x height x 4] */
  public static Tensor from(BufferedImage bufferedImage) {
    switch (bufferedImage.getType()) {
    case BufferedImage.TYPE_BYTE_GRAY:
      return Transpose.of(ImageFormat.from(bufferedImage));
    default:
      return Tensors.matrix((x, y) -> ColorFormat.toVector(bufferedImage.getRGB(x, y)), //
          bufferedImage.getWidth(), bufferedImage.getHeight());
    }
  }

  /** image export works with PNG format.
   * 
   * <p>do not export color images to JPG, because the color channels are not compatible!
   * 
   * @param tensor
   * @return image of type BufferedImage.TYPE_BYTE_GRAY or BufferedImage.TYPE_INT_ARGB */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2)
      return ImageFormat.toTYPE_BYTE_GRAY(Transpose.of(tensor), dims.get(0), dims.get(1));
    return toTYPE_INT_ARGB(tensor, dims.get(0), dims.get(1));
  }

  // implemented here to postpone transpose for as long as possible
  private static BufferedImage toTYPE_INT_ARGB(Tensor tensor, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Tensor argb = TensorMap.of(vector -> RealScalar.of(ColorFormat.toInt(vector)), tensor, 2);
    int[] array = Primitives.toIntArray(Transpose.of(argb));
    bufferedImage.setRGB(0, 0, width, height, array, 0, width);
    return bufferedImage;
  }
}
