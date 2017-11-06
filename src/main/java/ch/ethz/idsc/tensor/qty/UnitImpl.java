// code by jph
package ch.ethz.idsc.tensor.qty;

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
import ch.ethz.idsc.tensor.TensorRuntimeException;

/* package */ class UnitImpl implements Unit {
  private final NavigableMap<String, Scalar> navigableMap;

  /* package */ UnitImpl(Map<String, Scalar> map) {
    NavigableMap<String, Scalar> treeMap = new TreeMap<>();
    for (Entry<String, Scalar> entry : map.entrySet()) {
      Scalar exponent = entry.getValue();
      if (Scalars.nonZero(exponent))
        treeMap.put(entry.getKey(), exponent);
    }
    navigableMap = Collections.unmodifiableNavigableMap(treeMap);
  }

  @Override // from Unit
  public Unit negate() {
    return new UnitImpl(navigableMap.entrySet().stream() //
        .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().negate())));
  }

  @Override // from Unit
  public Unit add(Unit unit) {
    Map<String, Scalar> map = new HashMap<>(navigableMap);
    for (Entry<String, Scalar> entry : unit.map().entrySet()) {
      String key = entry.getKey();
      Scalar exponent = entry.getValue();
      map.put(key, map.containsKey(key) ? map.get(key).add(exponent) : exponent);
    }
    return new UnitImpl(map);
  }

  @Override // from Unit
  public Unit multiply(Scalar factor) {
    if (factor instanceof RealScalar)
      return new UnitImpl(navigableMap.entrySet().stream() //
          .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().multiply(factor))));
    throw TensorRuntimeException.of(factor);
  }

  @Override // from Unit
  public NavigableMap<String, Scalar> map() {
    return navigableMap;
  }

  /***************************************************/
  @Override // from Object
  public int hashCode() {
    return navigableMap.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof Unit) {
      Unit unit = (Unit) object;
      return navigableMap.equals(unit.map());
    }
    return false;
  }

  private static String exponentString(Scalar exponent) {
    String string = exponent.toString();
    return string.equals("1") ? "" : Unit.POWER_DELIMITER + string;
  }

  @Override // from Object
  public String toString() {
    return navigableMap.entrySet().stream() //
        .map(entry -> entry.getKey() + exponentString(entry.getValue())) //
        .collect(Collectors.joining(Unit.JOIN_DELIMITER));
  }
}
