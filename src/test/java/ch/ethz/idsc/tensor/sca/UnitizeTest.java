// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class UnitizeTest extends TestCase {
  public void testSimple() {
    Tensor res = Unitize.of(Tensors.vector(0, 0, 1e-3, -3, Double.NaN, 0));
    assertEquals(res, Tensors.vector(0, 0, 1, 1, 1, 0));
  }
}
