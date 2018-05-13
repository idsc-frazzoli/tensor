// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

/** function maps integer values from the interval [0, size() - 1] to vectors
 * of the form {R, G, B, A} with entries in the interval [0, 255]. */
public interface ColorDataIndexed extends ScalarTensorFunction {
  /** @param index in the interval [0, size() - 1]
   * @return */
  Color getColor(int index);

  /** @return number of available colors */
  int size();

  /** @param alpha
   * @return new instance of ColorDataIndexed with identical RGB color values
   * but with transparency as given alpha */
  ColorDataIndexed deriveWithAlpha(int alpha);
}
