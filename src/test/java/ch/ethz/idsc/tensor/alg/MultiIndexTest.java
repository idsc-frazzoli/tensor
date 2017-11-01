// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class MultiIndexTest extends TestCase {
  public void testSimple() {
    String string = MultiIndex.of(2, 3).toString();
    assertTrue(string instanceof String);
    assertEquals(string, "{2, 3}");
  }
}
