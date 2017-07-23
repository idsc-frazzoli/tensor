// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** class is intended for testing and demonstration */
/* package */ class Unit implements Serializable {
  static final char OPENING_BRACKET = '[';
  static final char CLOSING_BRACKET = ']';

  /** @param string, for instance "[m*s^-2]"
   * @return */
  public static Unit of(String string) {
    if (OPENING_BRACKET != string.charAt(0))
      throw new RuntimeException();
    if (CLOSING_BRACKET != string.charAt(string.length() - 1))
      throw new RuntimeException();
    string = string.substring(1, string.length() - 1); // remove brackets
    String[] split = string.split("\\*");
    Map<String, Scalar> map = new HashMap<>();
    for (int count = 0; count < split.length; ++count) {
      String token = split[count];
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
      map.put(unit.trim(), exponent);
    }
    return new Unit(map);
  }

  public static Unit singleton(String unit, Scalar exponent) {
    return new Unit(Collections.singletonMap(unit, exponent));
  }

  // ---
  private final NavigableMap<String, Scalar> navigableMap = new TreeMap<>();

  private Unit(Map<String, Scalar> map) {
    for (Entry<String, Scalar> entry : map.entrySet()) {
      final String string = entry.getKey();
      if (string.isEmpty())
        throw new RuntimeException();
      if (0 <= string.indexOf(OPENING_BRACKET))
        throw new RuntimeException();
      if (0 <= string.indexOf(CLOSING_BRACKET))
        throw new RuntimeException();
      Scalar exponent = entry.getValue();
      if (Scalars.nonZero(exponent))
        navigableMap.put(entry.getKey(), exponent);
    }
  }

  public boolean isEmpty() {
    return navigableMap.isEmpty();
  }

  public Unit negate() {
    Map<String, Scalar> map = new HashMap<>();
    for (Entry<String, Scalar> entry : navigableMap.entrySet())
      map.put(entry.getKey(), entry.getValue().negate());
    return new Unit(map);
  }

  public Unit add(Unit unit) {
    Map<String, Scalar> map = new HashMap<>(navigableMap);
    for (Entry<String, Scalar> entry : unit.navigableMap.entrySet()) {
      String key = entry.getKey();
      if (map.containsKey(key)) {
        Scalar exponent = map.get(key).add(entry.getValue());
        if (Scalars.nonZero(exponent))
          map.put(key, exponent);
        else
          map.remove(key);
      } else
        map.put(key, entry.getValue());
    }
    return new Unit(map);
  }

  public Unit multiply(Scalar factor) {
    Map<String, Scalar> map = new HashMap<>();
    for (Entry<String, Scalar> entry : navigableMap.entrySet())
      map.put(entry.getKey(), entry.getValue().multiply(factor));
    return new Unit(map);
  }

  @Override
  public int hashCode() {
    return navigableMap.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Unit) {
      Unit unit = (Unit) object;
      return navigableMap.equals(unit.navigableMap);
    }
    return false;
  }

  private static String exponentString(Scalar exponent) {
    String string = exponent.toString();
    return string.equals("1") ? "" : "^" + string;
  }

  @Override
  public String toString() {
    return OPENING_BRACKET + navigableMap.entrySet().stream() //
        .map(entry -> entry.getKey() + exponentString(entry.getValue())) //
        .collect(Collectors.joining("*")) + CLOSING_BRACKET;
  }
}
