// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class MultiIndexTest extends TestCase {
  public void testToString() {
    assertEquals(MultiIndex.of(2, 3).toString(), "{2, 3}");
  }
}
