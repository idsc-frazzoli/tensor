// code from Computer Graphics, by Donald Hearn and Pauline Baker
// adapted by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.N;

/** standalone implementation of hsv to rgb conversion
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Hue.html">Hue</a> */
public enum Hue implements ColorDataFunction {
  COLORDATA;
  // ---
  private static final Tensor TRANSPARENT = N.of(Array.zeros(4));

  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? //
        ColorFormat.toVector(of(scalar.number().doubleValue(), 1, 1, 1)) : TRANSPARENT.copy();
  }

  /** when saturation is close or equal to zero, the rgb values equate to input val
   * 
   * @param hue is periodically mapped to [0, 1)
   * @param sat in [0, 1] as "saturation"
   * @param val in [0, 1] as "value"
   * @param alpha in [0, 1] */
  public static Color of(double hue, double sat, double val, double alpha) {
    if (!Double.isFinite(hue))
      throw new RuntimeException("h=" + hue);
    // ---
    final double r;
    final double g;
    final double b;
    hue %= 1;
    if (hue < 0)
      hue += 1;
    hue *= 6;
    int i = (int) hue; // if isNaN(h) then i == 0
    double f = hue - i;
    double aa = val * (1 - sat); // if s==0 then aa=v
    double bb = val * (1 - sat * f); // if s==0 then bb=v
    double cc = val * (1 - sat * (1 - f)); // if s==0 then cc=v
    switch (i) {
    case 0:
      r = val;
      g = cc;
      b = aa;
      break;
    case 1:
      r = bb;
      g = val;
      b = aa;
      break;
    case 2:
      r = aa;
      g = val;
      b = cc;
      break;
    case 3:
      r = aa;
      g = bb;
      b = val;
      break;
    case 4:
      r = cc;
      g = aa;
      b = val;
      break;
    case 5:
    default:
      r = val;
      g = aa;
      b = bb;
      break;
    }
    return new Color((float) r, (float) g, (float) b, (float) alpha);
  }
}
