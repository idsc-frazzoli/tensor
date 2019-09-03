// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ArrayQTest extends TestCase {
  public void testScalar() {
    assertTrue(ArrayQ.of(RealScalar.ONE));
    assertTrue(ArrayQ.of(ComplexScalar.fromPolar(3.7, 9.8)));
    assertTrue(ArrayQ.of(Quantity.of(4, "m")));
  }

  public void testIsArray() {
    Tensor d = DoubleScalar.of(0.12);
    assertTrue(ArrayQ.of(d));
    assertTrue(ArrayQ.of(Tensors.empty()));
    Tensor a = Tensors.vectorLong(3, 2, 3);
    assertTrue(ArrayQ.of(a));
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of(a, b);
    assertFalse(ArrayQ.of(c));
  }

  public void testOfRank() {
    for (int rank = 0; rank < 5; ++rank) {
      assertEquals(rank == 0, ArrayQ.ofRank(RealScalar.ONE, rank));
      assertEquals(rank == 1, ArrayQ.ofRank(Tensors.vector(1, 2, 3), rank));
      assertEquals(rank == 2, ArrayQ.ofRank(HilbertMatrix.of(2, 3), rank));
      assertEquals(rank == 3, ArrayQ.ofRank(LieAlgebras.he1(), rank));
    }
  }

  public void testRequire() {
    Tensor tensor = Tensors.fromString("{{1, 2}, 3}");
    try {
      ArrayQ.require(tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      ArrayQ.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
