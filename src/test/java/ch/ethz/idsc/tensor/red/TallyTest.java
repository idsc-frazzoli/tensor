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
}
