// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testIdentity() {
    int[] permute = StaticHelper.permute(new int[] { 2, 3, 4 }, new int[] { 0, 1, 2 });
    assertEquals(permute[0], 2);
    assertEquals(permute[1], 3);
    assertEquals(permute[2], 4);
  }

  public void testRotate() {
    int[] permute = StaticHelper.permute(new int[] { 2, 3, 4 }, new int[] { 2, 0, 1 });
    assertEquals(permute[0], 3);
    assertEquals(permute[1], 4);
    assertEquals(permute[2], 2);
  }
}
