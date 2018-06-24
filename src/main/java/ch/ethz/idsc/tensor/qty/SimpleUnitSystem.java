// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import ch.ethz.idsc.tensor.sca.Power;

/** reference implementation of {@link UnitSystem} with emphasis on simplicity */
public class SimpleUnitSystem implements UnitSystem {
  /** given properties map a unit expression to a {@link Quantity}
   * 
   * <p>Example from the built-in file "/unit/si.properties":
   * <pre>
   * rad=1
   * Hz=1[s^-1]
   * N=1[m*kg*s^-2]
   * Pa=1[m^-1*kg*s^-2]
   * ...
   * </pre>
   * 
   * @param properties
   * @return
   * @throws Exception if keys do not define unit conversions */
  public static UnitSystem from(Properties properties) {
    return new SimpleUnitSystem(properties.stringPropertyNames().stream().collect(Collectors.toMap( //
        UnitHelper::requireValid, key -> requireNumeric(Scalars.fromString(properties.getProperty(key))))));
  }

  /** @param map
   * @return unit system */
  public static UnitSystem from(Map<String, Scalar> map) {
    return new SimpleUnitSystem(map.entrySet().stream().collect(Collectors.toMap( //
        entry -> UnitHelper.requireValid(entry.getKey()), entry -> requireNumeric(entry.getValue()))));
  }

  // ---
  private final Map<String, Scalar> map;

  private SimpleUnitSystem(Map<String, Scalar> map) {
    this.map = map;
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
    return Objects.requireNonNull(scalar);
  }

  @Override // from UnitSystem
  public Map<String, Scalar> map() {
    return Collections.unmodifiableMap(map);
  }

  // helper function
  private static Scalar requireNumeric(Scalar scalar) {
    if (StringScalarQ.of(scalar))
      throw TensorRuntimeException.of(scalar);
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      if (StringScalarQ.of(quantity.value()))
        throw TensorRuntimeException.of(scalar);
    }
    return scalar;
  }

  // helper function
  private static String format(Entry<String, Scalar> entry) {
    return entry.getKey() + Unit.POWER_DELIMITER + entry.getValue();
  }
}
