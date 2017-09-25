// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Map;

import ch.ethz.idsc.tensor.Scalar;

public interface Unit extends Serializable {
  /** Example: cd*m*s */
  static final String JOIN_DELIMITER = "*";
  /** Example: A*kg^-2 */
  static final String POWER_DELIMITER = "^";
  /** holds the dimension-less unit ONE */
  static final Unit ONE = of("");

  /** @param string, for instance "m*s^-2"
   * @return */
  static Unit of(String string) {
    return UnitHelper.MEMO.lookup(string);
  }

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
