// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class FoldListTest extends TestCase {
  public void testEmpty() {
    Tensor a = Tensors.vector();
    Tensor r = FoldList.of(Tensor::add, a);
    assertEquals(r, Tensors.empty());
  }

  public void testAddUp() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor r = FoldList.of(Tensor::add, a);
    assertEquals(r, Tensors.vector(1, 3, 6));
  }

  public void testFail() {
    try {
      FoldList.of(Tensor::add, RealScalar.of(31));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
