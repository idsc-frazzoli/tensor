// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TallyTest extends TestCase {
  public void testSome() {
    Tensor tensor = Tensors.vector(4, 2, 3, 7, 2, 5, 4, 2, 2, 5);
    Map<Tensor, Long> map = Tally.of(tensor);
    assertEquals((long) map.get(RealScalar.of(2)), 4);
    assertEquals((long) map.get(RealScalar.of(4)), 2);
    assertEquals((long) map.get(RealScalar.of(5)), 2);
  }

  public void testEmpty() {
    Map<Tensor, Long> map = Tally.of(Tensors.empty());
    assertEquals(map, Collections.emptyMap());
  }

  public void testInfty() {
    Tensor tensor = Tensors.of( //
        DoubleScalar.POSITIVE_INFINITY, RealScalar.ONE, //
        DoubleScalar.NEGATIVE_INFINITY, //
        DoubleScalar.POSITIVE_INFINITY, DoubleScalar.POSITIVE_INFINITY);
    Map<Tensor, Long> map = Tally.of(tensor);
    assertEquals((long) map.get(DoubleScalar.POSITIVE_INFINITY), 3);
    assertEquals((long) map.get(DoubleScalar.NEGATIVE_INFINITY), 1);
    assertEquals((long) map.get(RealScalar.of(1)), 1);
  }

  public void testSorted() {
    Tensor vector = Tensors.vector(4, 2, 3, 7, 2, 5, 4, 2, 2, 5);
    NavigableMap<Tensor, Long> navigableMap = Tally.sorted(vector);
    Tensor keys = Tensor.of(navigableMap.keySet().stream());
    assertEquals(keys, Tensors.vector(2, 3, 4, 5, 7));
  }

  public void testNumZero() {
    Tensor vector = Tensors.vector(-0.0, 0.0, -0.0);
    assertEquals(Tally.of(vector).size(), 1);
  }

  public void testFail() {
    try {
      Tally.of(RealScalar.of(3.1234));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
