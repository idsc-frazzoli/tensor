// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

public interface ColorDataIndexed extends ScalarTensorFunction {
  /** @param index
   * @return */
  Color getColor(int index);

  /** @return number of available colors */
  int size();
}
