// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RescaleTest extends TestCase {
  public void testScalar() {
    try {
      Rescale.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrix() {
    try {
      Rescale.of(Tensors.fromString("{{1,2,3}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmpty() {
    assertEquals(Rescale.of(Tensors.empty()), Tensors.empty());
  }

  public void testEqual() {
    assertEquals(Rescale.of(Tensors.vector(2, 2, 2, 2)), Array.zeros(4));
  }

  public void testSimple() {
    Tensor res = Rescale.of(Tensors.vector(-.7, .5, 1.2, 5.6, 1.8));
    Tensor sol = Tensors.vector(0., 0.190476, 0.301587, 1., 0.396825);
    assertEquals(res.subtract(sol).map(Chop.below(.00001)), Array.zeros(5));
  }
}
