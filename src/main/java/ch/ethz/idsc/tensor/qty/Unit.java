// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Map;

import ch.ethz.idsc.tensor.Scalar;

public interface Unit extends Serializable {
  /** m*s */
  static final String JOIN = "*";
  /** kg^-2 */
  static final String POWER = "^";

  /** @param string, for instance "m*s^-2"
   * @return */
  static Unit of(String string) {
    return Units.MEMO.lookup(string);
  }

  /** @return true if unit is unit-less */
  boolean isEmpty();

  /** [kg*m^2] -> [kg^-1*m^-2]
   * 
   * function negate is equivalent to {@link #multiply(Scalar)} with factor -1
   * 
   * @return */
  Unit negate();

  /** [m*s] + [s^2] -> [m*s^3]
   * 
   * @param unit
   * @return */
  Unit add(Unit unit);

  /** [kg*m^2] * 3 -> [kg^3*m^6]
   * 
   * @param factor
   * @return */
  Unit multiply(Scalar factor);

  /** @return map with elemental units and exponents */
  Map<String, Scalar> map();
}
