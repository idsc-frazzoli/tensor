// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Collections;
import java.util.Map;

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
        RealScalar.POSITIVE_INFINITY, RealScalar.ONE, //
        RealScalar.NEGATIVE_INFINITY, //
        RealScalar.POSITIVE_INFINITY, RealScalar.POSITIVE_INFINITY);
    Map<Tensor, Long> map = Tally.of(tensor);
    assertEquals((long) map.get(RealScalar.POSITIVE_INFINITY), 3);
    assertEquals((long) map.get(RealScalar.NEGATIVE_INFINITY), 1);
    assertEquals((long) map.get(RealScalar.of(1)), 1);
  }
}
