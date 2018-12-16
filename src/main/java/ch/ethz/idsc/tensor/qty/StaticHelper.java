// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;

enum StaticHelper {
  ;
  static Set<String> all(UnitSystem unitSystem) {
    Set<String> set = new HashSet<>();
    for (Entry<String, Scalar> entry : unitSystem.map().entrySet()) {
      set.add(entry.getKey());
      Scalar value = entry.getValue();
      if (value instanceof Quantity) {
        Quantity quantity = (Quantity) value;
        set.addAll(quantity.unit().map().keySet());
      }
    }
    return set;
  }
}
