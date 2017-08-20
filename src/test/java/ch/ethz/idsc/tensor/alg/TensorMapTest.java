// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class TensorMapTest extends TestCase {
  public void testUnmodifiable() {
    Tensor matrix = Array.zeros(3, 1).unmodifiable();
    try {
      TensorMap.of(s -> {
        s.set(RealScalar.ONE, 0);
        return s;
      }, matrix, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    assertEquals(matrix, Array.zeros(3, 1));
  }

  public void testTotal() {
    Tensor tensor = Tensors.fromString("{{1,2,3},{4,5}}");
    Tensor result = TensorMap.of(Total::of, tensor, 1);
    assertEquals(result, Tensors.vector(6, 9));
  }

  public void testIrregular() {
    Tensor array = Tensors.fromString("{{1,2,3},{8,9}}");
    Tensor result = TensorMap.of(row -> Total.of(row), array, 1);
    assertEquals(array, Tensors.fromString("{{1,2,3},{8,9}}"));
    assertEquals(result, Tensors.vector(6, 17));
  }

  public void testModifiable() {
    Tensor matrix = Array.zeros(3, 1);
    Tensor blub = TensorMap.of(s -> {
      s.set(RealScalar.ONE, 0);
      return s;
    }, matrix, 1);
    assertEquals(matrix, Array.zeros(3, 1).map(Increment.ONE));
    assertEquals(matrix, blub);
    assertFalse(matrix == blub);
  }
}
