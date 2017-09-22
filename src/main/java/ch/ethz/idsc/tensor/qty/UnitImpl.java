// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class UnitImpl implements Unit {
  private static final Pattern UNIT_PATTERN = Pattern.compile("\\w+");
  // ---
  private final NavigableMap<String, Scalar> navigableMap = new TreeMap<>();

  /* package */ UnitImpl(Map<String, Scalar> map) {
    for (Entry<String, Scalar> entry : map.entrySet()) {
      final String string = entry.getKey();
      if (!UNIT_PATTERN.matcher(string).matches())
        throw new RuntimeException(string);
      Scalar exponent = entry.getValue();
      if (Scalars.nonZero(exponent))
        navigableMap.put(entry.getKey(), exponent);
    }
  }

  @Override
  public boolean isEmpty() {
    return navigableMap.isEmpty();
  }

  @Override
  public Unit negate() {
    return new UnitImpl(navigableMap.entrySet().stream() //
        .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().negate())));
  }

  @Override
  public Unit add(Unit unit) {
    Map<String, Scalar> map = new HashMap<>(navigableMap);
    for (Entry<String, Scalar> entry : ((UnitImpl) unit).navigableMap.entrySet()) {
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
    return new UnitImpl(map);
  }

  @Override
  public Unit multiply(Scalar factor) {
    return new UnitImpl(navigableMap.entrySet().stream() //
        .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().multiply(factor))));
  }

  @Override
  public int hashCode() {
    return navigableMap.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Unit) {
      UnitImpl unit = (UnitImpl) object;
      return navigableMap.equals(unit.navigableMap);
    }
    return false;
  }

  private static String exponentString(Scalar exponent) {
    String string = exponent.toString();
    return string.equals("1") ? "" : Unit.POWER + string;
  }

  @Override
  public String toString() {
    return navigableMap.entrySet().stream() //
        .map(entry -> entry.getKey() + exponentString(entry.getValue())) //
        .collect(Collectors.joining(Unit.JOIN));
  }

  @Override
  public NavigableMap<String, Scalar> map() {
    return Collections.unmodifiableNavigableMap(navigableMap);
  }
}
