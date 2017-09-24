// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Power;

/** reference implementation of {@link UnitSystem} with emphasis on simplicity */
public class SimpleUnitSystem implements UnitSystem {
  /** @param properties
   * @return */
  public static UnitSystem from(Properties properties) {
    SimpleUnitSystem simpleUnitSystem = new SimpleUnitSystem();
    for (String key : properties.stringPropertyNames())
      simpleUnitSystem.map.put(key, Quantity.fromString(properties.getProperty(key)));
    return simpleUnitSystem;
  }

  private final Map<String, Scalar> map = new HashMap<>();

  private SimpleUnitSystem() {
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof Quantity) {
      final Quantity quantity = (Quantity) scalar;
      Scalar value = quantity.value();
      for (Entry<String, Scalar> entry : quantity.unit().map().entrySet()) {
        Scalar lookup = map.get(entry.getKey());
        value = value.multiply(Objects.isNull(lookup) //
            ? Quantity.of(RealScalar.ONE, entry.getKey() + Unit.POWER + entry.getValue()) //
            : Power.of(lookup, entry.getValue()));
      }
      return value;
    }
    return scalar;
  }
}
