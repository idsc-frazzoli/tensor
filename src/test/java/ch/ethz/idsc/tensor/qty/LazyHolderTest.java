// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashMap;
import java.util.Map;

import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class LazyHolderTest extends TestCase {
  public void testInstances() {
    assertEquals(UnitSystem.SI(), LazyHolder.SI.unitSystem);
  }

  public void testExtension() {
    Map<String, Scalar> map = new HashMap<>(UnitSystem.SI().map());
    map.put("CHF", Quantity.of(3, "m"));
    UnitSystem unitSystem = SimpleUnitSystem.from(map);
    KnownUnitQ knownUnitQ = KnownUnitQ.in(unitSystem);
    assertTrue(knownUnitQ.of(Unit.of("CHF^2*K")));
  }
}
