// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class NumelTest extends TestCase {
  public void testNumel() {
    assertEquals(Numel.of(Tensors.empty()), 0);
    Tensor a = DoubleScalar.of(2.32123);
    assertEquals(Numel.of(a), 1);
    Tensor b = Tensors.vectorLong(3, 2);
    assertEquals(Numel.of(b), 2);
    Tensor c = DoubleScalar.of(1.23);
    assertEquals(Numel.of(c), 1);
    Tensor d = Tensors.of(a, b, c);
    assertEquals(Numel.of(d), 4);
    assertEquals(Numel.of(Array.zeros(3, 5, 4)), 3 * 4 * 5);
  }
}
