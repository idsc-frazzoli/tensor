// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Properties;

import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class SimpleUnitSystemTest extends TestCase {
  public void testMap() {
    Properties properties = new Properties();
    properties.setProperty("cent", "1/100[FRA]");
    UnitSystem unitSystem = SimpleUnitSystem.from(properties);
    Scalar scalar = unitSystem.apply(Quantity.of(100, "cent"));
    assertEquals(scalar, Quantity.of(1, "FRA"));
    assertEquals(unitSystem.map().size(), 1);
  }

  public void testFailKey1() {
    Properties properties = new Properties();
    properties.setProperty("cent123", "1/100[FRA]");
    try {
      SimpleUnitSystem.from(properties);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailKey2() {
    Properties properties = new Properties();
    properties.setProperty(" cent", "1/100[FRA]");
    try {
      SimpleUnitSystem.from(properties);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailValue1() {
    Properties properties = new Properties();
    properties.setProperty("cent", "1/100a[FRA]");
    try {
      SimpleUnitSystem.from(properties);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailValue2() {
    Properties properties = new Properties();
    properties.setProperty("cent", "b/100a");
    try {
      SimpleUnitSystem.from(properties);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDerive() {
    UnitSystem der = SimpleUnitSystem.from(UnitSystem.SI().map());
    assertEquals(der.map(), UnitSystem.SI().map());
  }
}
