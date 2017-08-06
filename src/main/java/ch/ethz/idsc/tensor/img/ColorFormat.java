// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.io.Primitives;

/** mappings between {@link Tensor}, {@link Color}, and 0xAA:RR:GG:BB integer
 * 
 * <p>functions are used in {@link ImageFormat} */
public enum ColorFormat {
  ;
  /** @param argb encoding color as 0xAA:RR:GG:BB
   * @return vector with {@link Scalar} entries as {R, G, B, A} */
  public static Tensor toVector(int argb) {
    return toVector(new Color(argb, true));
  }

  /** @param color
   * @return vector with {@link Scalar} entries as {R, G, B, A} */
  public static Tensor toVector(Color color) {
    return Tensors.vector(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  /** @param vector with {@link Scalar} entries as {R, G, B, A}
   * @return encoding color as 0xAA:RR:GG:BB */
  public static int toInt(Tensor vector) {
    return toColor(vector).getRGB();
  }

  /** @param vector with {@link Scalar} entries as {R, G, B, A}
   * @return int in hex 0xAA:RR:GG:BB */
  public static Color toColor(Tensor vector) {
    int[] rgba = Primitives.toArrayInt(vector);
    return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
  }
}
