// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Set;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testAll() {
    Set<String> set = StaticHelper.all(UnitSystem.SI());
    assertTrue(set.contains("cd"));
    assertTrue(set.contains("m"));
    assertTrue(set.contains("kg"));
    assertTrue(set.contains("K"));
    assertTrue(set.contains("CD"));
    assertTrue(set.contains("V"));
    assertFalse(set.contains("CHF"));
    assertFalse(set.contains("EUR"));
    assertFalse(set.contains("USD"));
  }
}
