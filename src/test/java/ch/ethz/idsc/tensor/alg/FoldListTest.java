// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class FoldListTest extends TestCase {
  public void testEmpty() {
    Tensor a = Tensors.vector();
    Tensor r = FoldList.of(Tensor::add, a);
    assertEquals(r, Tensors.empty());
  }
}
