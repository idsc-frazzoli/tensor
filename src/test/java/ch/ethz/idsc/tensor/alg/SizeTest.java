// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class SizeTest extends TestCase {
  public void testIndexOf() {
    Size size = Size.of(new int[] { 4, 2, 3 });
    assertEquals(size.indexOf(MultiIndex.of(0, 0, 0)), 0);
    assertEquals(size.indexOf(MultiIndex.of(3, 1, 2)), 23);
  }

  public void testString() {
    Size size = Size.of(new int[] { 4, 2, 3 });
    String string = size.toString();
    assertTrue(string instanceof String);
  }
}
