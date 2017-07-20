// code by jph
package ch.ethz.idsc.tensor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** class is intended for testing and demonstration
 * the API is trimmed for elegance */
public class UnitMap {
  private final NavigableMap<String, Scalar> navigableMap = new TreeMap<>();

  public UnitMap(String unit, Scalar exponent) {
    this(Collections.singletonMap(unit, exponent));
  }

  // TODO not final design
  public static UnitMap of(String unit1, Scalar exponent1, String unit2, Scalar exponent2) {
    Map<String, Scalar> map = new HashMap<>();
    map.put(unit1, exponent1);
    map.put(unit2, exponent2);
    return new UnitMap(map);
  }

  public UnitMap(Map<String, Scalar> map) {
    for (Entry<String, Scalar> entry : map.entrySet()) {
      Scalar exponent = entry.getValue();
      if (Scalars.nonZero(exponent))
        navigableMap.put(entry.getKey(), exponent);
    }
  }

  public boolean isEmpty() {
    return navigableMap.isEmpty();
  }

  public UnitMap negate() {
    Map<String, Scalar> map = new HashMap<>();
    for (Entry<String, Scalar> entry : navigableMap.entrySet())
      map.put(entry.getKey(), entry.getValue().negate());
    return new UnitMap(map);
  }

  public UnitMap add(UnitMap unitMap) {
    Map<String, Scalar> map = new HashMap<>(navigableMap);
    for (Entry<String, Scalar> entry : unitMap.navigableMap.entrySet()) {
      String unit = entry.getKey();
      if (map.containsKey(unit)) {
        Scalar exponent = map.get(unit).add(entry.getValue());
        if (Scalars.nonZero(exponent))
          map.put(unit, exponent);
        else
          map.remove(unit);
      } else
        map.put(unit, entry.getValue());
    }
    return new UnitMap(map);
  }

  public UnitMap multiply(Scalar factor) {
    Map<String, Scalar> map = new HashMap<>();
    for (Entry<String, Scalar> entry : navigableMap.entrySet())
      map.put(entry.getKey(), entry.getValue().multiply(factor));
    return new UnitMap(map);
  }

  @Override
  public int hashCode() {
    return navigableMap.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof UnitMap) {
      UnitMap unitMap = (UnitMap) object;
      return navigableMap.equals(unitMap.navigableMap);
    }
    return false;
  }

  @Override
  public String toString() {
    return navigableMap.entrySet().stream() //
        .map(entry -> entry.getKey() + "^" + entry.getValue()) //
        .collect(Collectors.joining(","));
  }
}
