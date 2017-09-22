// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

public interface Unit extends Serializable {
  /** m*s */
  static final String JOIN = "*";
  /** kg^-2 */
  static final String POWER = "^";

  /** @param string, for instance "m*s^-2"
   * @return */
  static Unit of(String string) {
    Map<String, Scalar> map = new HashMap<>();
    StringTokenizer stringTokenizer = new StringTokenizer(string, Unit.JOIN);
    while (stringTokenizer.hasMoreTokens()) {
      final String token = stringTokenizer.nextToken();
      int index = token.indexOf('^');
      final String unit;
      final Scalar exponent;
      if (0 <= index) {
        unit = token.substring(0, index);
        exponent = Scalars.fromString(token.substring(index + 1));
      } else {
        unit = token;
        exponent = RealScalar.ONE;
      }
      final String key = unit.trim();
      map.put(key, map.containsKey(key) ? map.get(key).add(exponent) : exponent);
    }
    return new UnitImpl(map);
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
