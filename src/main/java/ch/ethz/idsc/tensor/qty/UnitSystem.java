// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitSystem.html">UnitSystem</a> */
public class UnitSystem implements ScalarUnaryOperator {
  private static UnitSystem SI = from(ResourceData.properties("/unit/si.properties"));

  /** @return international system of units, metric system */
  public static UnitSystem SI() {
    return SI;
  }

  public static UnitSystem from(Properties properties) {
    UnitSystem quantityConverter = new UnitSystem();
    for (String key : properties.stringPropertyNames()) {
      String string = properties.getProperty(key);
      quantityConverter.map.put(key, Quantity.fromString(string));
    }
    return quantityConverter;
  }

  private final Map<String, Scalar> map = new HashMap<>();

  private UnitSystem() {
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

  /* package */ void print() {
    map.entrySet().forEach(System.out::println);
  }
}
