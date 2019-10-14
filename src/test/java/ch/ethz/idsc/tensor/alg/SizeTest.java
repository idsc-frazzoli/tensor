// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import junit.framework.TestCase;

public class SizeTest extends TestCase {
  public void testIndexOf() {
    Size size = Size.of(new int[] { 4, 2, 3 });
    assertEquals(size.indexOf(new MultiIndex(Arrays.asList(0, 0, 0))), 0);
    assertEquals(size.indexOf(new MultiIndex(Arrays.asList(3, 1, 2))), 23);
  }

  public void testString() {
    Size size = Size.of(new int[] { 4, 2, 3 });
    String string = size.toString();
    assertFalse(string.isEmpty());
  }
}
