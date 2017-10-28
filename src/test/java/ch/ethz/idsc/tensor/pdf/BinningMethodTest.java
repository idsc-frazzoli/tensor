// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class BinningMethodTest extends TestCase {
  public void testSimple() {
    Scalar width = BinningMethod.SQRT.apply(Tensors.vector(2, 4, 3, 6));
    assertEquals(width, RealScalar.of(2));
  }

  public void testFail() {
    for (BinningMethod bm : BinningMethod.values())
      try {
        bm.apply(Tensors.empty());
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
  }
}
