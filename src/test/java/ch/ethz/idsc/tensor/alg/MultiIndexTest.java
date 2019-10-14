// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import junit.framework.TestCase;

public class MultiIndexTest extends TestCase {
  public void testToString() {
    assertEquals(new MultiIndex(Arrays.asList(2, 3)).toString(), "{2, 3}");
  }
}
