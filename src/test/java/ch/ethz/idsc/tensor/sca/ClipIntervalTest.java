// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipIntervalTest extends TestCase {
  public void testEqualsInterval() {
    assertEquals(Clip.function(3, 7), Clip.function(3, 7));
    assertFalse(Clip.function(3, 7).equals(Clip.function(3, 8)));
  }

  public void testEqualsQuantity() {
    Clip c1 = Clip.function(3, 7);
    Clip c2 = Clip.function(Quantity.of(2, "m"), Quantity.of(3, "m"));
    assertFalse(c1.equals(c2));
    assertFalse(c2.equals(c1));
    assertFalse(c1.equals(null));
    assertFalse(c2.equals(null));
    assertTrue(c1.equals(c1));
    assertTrue(c2.equals(c2));
  }

  public void testEqualsPoint() {
    assertEquals(Clip.function(7, 7), Clip.function(7, 7));
    assertFalse(Clip.function(7, 7).equals(Clip.function(7, 8)));
  }

  public void testHash() {
    assertEquals(Clip.function(7, 7).hashCode(), Clip.function(7, 7).hashCode());
    assertEquals(Clip.function(3, 7).hashCode(), Clip.function(3, 7).hashCode());
    assertFalse(Clip.function(3, 7).hashCode() == Clip.function(3, 8).hashCode());
  }
}
