// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;

/** The {@link Dimensions} of tensors that represent images are
 * WIDTH x HEIGHT x 4
 * 
 * The 4 entries in the last dimension are RGBA.
 * 
 * This convention is consistent with Java
 * {@link Graphics2D}, {@link BufferedImage}, ...
 * 
 * Tensor::get(x,y)
 * refers to the same pixel as
 * BufferedImage::getRGB(x,y)
 * 
 * Consistent also with the screen size, for instance 1280 x 720. */
public enum ImageFormat {
  ;
  // ---
  /** @param bufferedImage with dimensions [width x height]
   * @return tensor with dimensions [width x height x 4] */
  public static Tensor from(BufferedImage bufferedImage) {
    return Tensors.matrix((i, j) -> asVector(bufferedImage.getRGB(i, j)), //
        bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  /** @param bufferedImage grayscale image with dimensions [width x height]
   * @return tensor with dimensions [width x height] */
  public static Tensor fromGrayscale(BufferedImage bufferedImage) {
    return Tensors.matrix((i, j) -> RealScalar.of(bufferedImage.getRGB(i, j) & 0xff), //
        bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  /** this has been tested with PNG format
   * but with export to JPG there are somehow discrepancy !?
   * 
   * @param tensor
   * @return */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2) {
      BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_BYTE_GRAY);
      int[] array = ExtractPrimitives.toArrayInt(Transpose.of(tensor));
      for (int c = 0; c < array.length; ++c)
        array[c] = (array[c] << 16) | (array[c] << 8) | (array[c] << 0);
      bufferedImage.setRGB(0, 0, dims.get(0), dims.get(1), array, 0, dims.get(0));
      return bufferedImage;
    }
    BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_INT_ARGB);
    Tensor res = TensorMap.of(t -> RealScalar.of(asARGB(t)), tensor, 2);
    int[] array = ExtractPrimitives.toArrayInt(Transpose.of(res));
    bufferedImage.setRGB(0, 0, dims.get(0), dims.get(1), array, 0, dims.get(0));
    return bufferedImage;
  }

  // helper function
  private static Tensor asVector(int rgba) {
    Color color = new Color(rgba, true);
    return Tensors.vectorInt(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  // helper function
  private static int asARGB(Tensor vector) {
    if (vector.length() != 4)
      throw TensorRuntimeException.of(vector);
    int[] rgba = ExtractPrimitives.toArrayInt(vector);
    return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
  }
}
