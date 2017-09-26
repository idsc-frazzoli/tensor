// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** associates strings with instances of unit */
/* package */ enum UnitHelper {
  MEMO;
  // ---
  private static final int SIZE = 500;
  // ---
  private final Map<String, Unit> map = new LinkedHashMap<String, Unit>(SIZE * 4 / 3, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Unit> eldest) {
      return size() > SIZE;
    }
  };

  /** @param string, for instance "A*kg^-1*s^2"
   * @return unit */
  /* package */ Unit lookup(String string) {
    Unit unit = map.get(string);
    if (Objects.isNull(unit)) {
      unit = create(string);
      map.put(string, unit);
    }
    return unit;
  }

  // helper function
  private static Unit create(String string) {
    Map<String, Scalar> map = new HashMap<>();
    StringTokenizer stringTokenizer = new StringTokenizer(string, Unit.JOIN_DELIMITER);
    while (stringTokenizer.hasMoreTokens()) {
      String token = stringTokenizer.nextToken();
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
      String key = unit.trim();
      map.put(key, map.containsKey(key) ? map.get(key).add(exponent) : exponent);
    }
    return new UnitImpl(map);
  }
}
