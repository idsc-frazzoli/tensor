// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MedianTest extends TestCase {
  public void testEven() {
    /** Median[{1, 2, 3, 4, 5, 6, 7, 8}] == 9/2 */
    Tensor vector = Tensors.vector(1, 2, 3, 4, 5, 6, 7, 8);
    assertEquals(Median.of(vector), RationalScalar.of(9, 2));
    assertEquals(Median.of(Tensors.fromString("{1, 2}")), RationalScalar.of(3, 2));
  }

  public void testOdd() {
    /** Median[{1, 2, 3, 4, 5, 6, 7}] == 4 */
    Tensor vector = Tensors.vector(1, 2, 3, 4, 5, 6, 7);
    assertEquals(Median.of(vector), RealScalar.of(4));
    assertEquals(Median.of(Tensors.fromString("{1}")), RationalScalar.of(1, 1));
  }

  public void testEmpty() {
    try {
      Median.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
