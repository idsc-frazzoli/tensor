// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ArrayReshapeTest extends TestCase {
  public void testReshape() {
    Tensor s = Tensors.vector(1, 2, 3, 4, 5, 6);
    Tensor r = ArrayReshape.of(s, 2, 3, 1);
    assertEquals(r.toString(), "{{{1}, {2}, {3}}, {{4}, {5}, {6}}}");
  }

  public void testFail() {
    Tensor s = Tensors.vector(1, 2, 3, 4, 5, 6);
    ArrayReshape.of(s, 2, 3);
    try {
      ArrayReshape.of(s, 3, 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
