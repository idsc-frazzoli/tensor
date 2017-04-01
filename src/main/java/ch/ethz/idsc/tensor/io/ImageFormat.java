// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;

public enum ImageFormat {
  ;
  // ---
  /** @param bufferedImage
   * @return tensor with dimensions [height x width x 4] for RGBA images
   * tensor with dimensions [height x width] for gray scale images */
  public static Tensor from(BufferedImage bufferedImage) {
    int rows = bufferedImage.getHeight();
    int cols = bufferedImage.getWidth();
    // TODO test if this ij-ji-toggle is good in practice
    return Tensors.matrix((i, j) -> _from(bufferedImage.getRGB(j, i)), rows, cols);
  }

  /** @param tensor
   * @return */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    BufferedImage bufferedImage = new BufferedImage(dims.get(1), dims.get(0), BufferedImage.TYPE_INT_ARGB);
    int i = 0;
    for (Tensor row : tensor) {
      int j = 0;
      for (Tensor col : row) {
        bufferedImage.setRGB(j, i, _of(col));
        ++j;
      }
      ++i;
    }
    return bufferedImage;
  }

  // helper function
  private static Tensor _from(int rgba) {
    Color color = new Color(rgba, true);
    return Tensors.vectorInt(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  // helper function
  private static int _of(Tensor vector) {
    int[] rgba = ExtractPrimitives.vectorToArrayInt(vector);
    return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
  }
}
