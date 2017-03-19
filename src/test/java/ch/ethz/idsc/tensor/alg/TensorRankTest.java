// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TensorRankTest extends TestCase {
  public void testRank() {
    Tensor a = DoubleScalar.of(2.32123);
    assertEquals(TensorRank.of(a), 0);
  }

  public void testRank2() {
    Tensor a = Tensors.empty();
    assertEquals(TensorRank.of(a), 1);
  }

  public void testRank3() {
    Tensor a = Tensors.vectorLong(3, 2, 3);
    assertEquals(TensorRank.of(a), 1);
    Tensor b = Tensors.vectorLong(3, 2, 9);
    Tensor d = Tensors.of(a, b);
    assertEquals(TensorRank.of(d), 2);
  }

  public void testRank4() {
    Tensor a = DoubleScalar.of(2.32123);
    assertEquals(TensorRank.of(a), 0);
    Tensor b = Tensors.vectorLong(3, 2);
    assertEquals(TensorRank.of(b), 1);
    Tensor c = DoubleScalar.of(1.23);
    assertEquals(TensorRank.of(c), 0);
    Tensor d = Tensors.of(a, b, c);
    assertEquals(TensorRank.of(d), 1);
  }

  public void testRank5() {
    Tensor a = Tensors.vectorLong(1, 3, 2);
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of(a, b);
    assertEquals(TensorRank.of(c), 1);
  }
}
