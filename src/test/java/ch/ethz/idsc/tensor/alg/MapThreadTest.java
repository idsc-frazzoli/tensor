// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Collections;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class MapThreadTest extends TestCase {
  public void testSimple() {
    MapThread.of(l -> l.get(0), Collections.emptyList(), 1);
    MapThread.of(l -> l.get(0), Collections.emptyList(), 2);
    try {
      MapThread.of(l -> l.get(0), Collections.emptyList(), 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSome() {
    Tensor result = MapThread.of(l -> ComplexScalar.I, Collections.emptyList(), 0);
    assertEquals(ComplexScalar.I, result);
  }
}
