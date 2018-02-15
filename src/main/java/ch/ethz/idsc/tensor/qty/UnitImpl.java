// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/* package */ class UnitImpl implements Unit, Serializable {
  private final NavigableMap<String, Scalar> navigableMap;

  UnitImpl(NavigableMap<String, Scalar> navigableMap) {
    this.navigableMap = Collections.unmodifiableNavigableMap(navigableMap);
  }

  @Override // from Unit
  public Unit negate() {
    return new UnitImpl(navigableMap.entrySet().stream().collect(Collectors.toMap( //
        Entry::getKey, entry -> entry.getValue().negate(), (e1, e2) -> null, TreeMap::new)));
  }

  @Override // from Unit
  public Unit add(Unit unit) {
    NavigableMap<String, Scalar> map = new TreeMap<>(navigableMap);
    for (Entry<String, Scalar> entry : unit.map().entrySet()) {
      String key = entry.getKey();
      Scalar value = entry.getValue();
      if (map.containsKey(key)) {
        Scalar sum = map.get(key).add(value);
        if (Scalars.isZero(sum))
          map.remove(key); // exponents cancel out
        else
          map.put(key, sum); // exponent is updated
      } else
        map.put(key, value); // unit is introduced
    }
    return new UnitImpl(map);
  }

  @Override // from Unit
  public Unit multiply(Scalar factor) {
    if (factor instanceof RealScalar) {
      NavigableMap<String, Scalar> map = new TreeMap<>();
      for (Entry<String, Scalar> entry : navigableMap.entrySet()) {
        Scalar value = entry.getValue().multiply(factor);
        if (Scalars.nonZero(value))
          map.put(entry.getKey(), value);
      }
      return new UnitImpl(map);
    }
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
    return object instanceof Unit && navigableMap.equals(((Unit) object).map());
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
