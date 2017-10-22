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
  /** given properties map a unit expression to a {@link Quantity}
   * 
   * Example from the built-in file "/unit/si.properties":
   * <pre>
   * rad=1
   * Hz =1[s^-1]
   * N =1[m*kg*s^-2]
   * Pa =1[m^-1*kg*s^-2]
   * ...
   * </pre>
   * 
   * @param properties
   * @return */
  public static UnitSystem from(Properties properties) {
    SimpleUnitSystem simpleUnitSystem = new SimpleUnitSystem();
    for (String key : properties.stringPropertyNames())
      simpleUnitSystem.map.put(key, Quantity.fromString(properties.getProperty(key)));
    return simpleUnitSystem;
  }
  // ---

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
            ? Quantity.of(RealScalar.ONE, format(entry)) //
            : Power.of(lookup, entry.getValue()));
      }
      return value;
    }
    return scalar;
  }

  // helper function
  private static String format(Entry<String, Scalar> entry) {
    return entry.getKey() + Unit.POWER_DELIMITER + entry.getValue();
  }
}
