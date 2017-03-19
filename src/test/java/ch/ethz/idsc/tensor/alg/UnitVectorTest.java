// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class UnitVectorTest extends TestCase {
  public void testUnitvector() {
    assertEquals(UnitVector.of(3, 10), IdentityMatrix.of(10).get(3));
  }
}
