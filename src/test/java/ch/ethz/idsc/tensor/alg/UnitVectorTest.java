// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class UnitVectorTest extends TestCase {
  public void testRegular() {
    assertEquals(UnitVector.of(10, 3), IdentityMatrix.of(10).get(3));
  }

  public void testFail() {
    try {
      UnitVector.of(0, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UnitVector.of(-1, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UnitVector.of(3, -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UnitVector.of(3, 4);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UnitVector.of(10, 10);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
