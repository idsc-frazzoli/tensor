// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

/** function maps {@link Scalar}s to vectors of the form {R, G, B, A} with entries in the interval [0, 255].
 * implementations are immutable.
 * 
 * <p>The tensor library provides the implementations
 * {@link CyclicColorDataIndexed}
 * {@link StrictColorDataIndexed}
 * 
 * <p>The tensor library provides the color data
 * {@link ColorDataLists}
 * 
 * <p>since ColorDataIndexed extends from ScalarTensorFunction
 * every instance also implements the serializable interface. */
public interface ColorDataIndexed extends ScalarTensorFunction {
  /** @param index
   * @return color associated to given index */
  Color getColor(int index);

  /** @return number of unique colors defined by the instance */
  int length();

  /** @param alpha in the interval [0, 1, ..., 255]
   * @return new instance of ColorDataIndexed with identical RGB color values
   * but with transparency as given alpha
   * @throws Exception if alpha is not in the valid range */
  ColorDataIndexed deriveWithAlpha(int alpha);
}
