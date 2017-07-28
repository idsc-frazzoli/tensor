// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class NumelTest extends TestCase {
  public void testScalar() {
    Tensor a = DoubleScalar.of(2.32123);
    assertEquals(Numel.of(a), 1);
  }

  public void testNumel() {
    assertEquals(Numel.of(Tensors.empty()), 0);
    Tensor b = Tensors.vectorLong(3, 2);
    assertEquals(Numel.of(b), 2);
    Tensor c = DoubleScalar.of(1.23);
    assertEquals(Numel.of(c), 1);
    Tensor d = Tensors.of(DoubleScalar.of(2.32123), b, c);
    assertEquals(Numel.of(d), 4);
    assertEquals(Numel.of(Array.zeros(3, 5, 4)), 3 * 4 * 5);
  }

  public void testFlatten() {
    Tensor a = DoubleScalar.of(1.23);
    Tensor b = Tensors.vectorLong(3, 2, 3, 5);
    Tensor c = Tensors.vectorLong(3, 2, 5);
    Tensor f = Tensors.of(a, b, c);
    assertEquals(Numel.of(f), 8);
  }
}
