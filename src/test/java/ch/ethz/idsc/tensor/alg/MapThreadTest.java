// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class MapThreadTest extends TestCase {
  public void testEmptyPositive() {
    assertEquals(MapThread.of(l -> l.get(0), Collections.emptyList(), 1), Tensors.empty());
    assertEquals(MapThread.of(l -> l.get(0), Collections.emptyList(), 2), Tensors.empty());
    try {
      MapThread.of(l -> l.get(0), Collections.emptyList(), 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyZero() {
    Tensor result = MapThread.of(l -> ComplexScalar.I, Collections.emptyList(), 0);
    assertEquals(ComplexScalar.I, result);
  }

  public void testFail() {
    List<Tensor> list = Arrays.asList(HilbertMatrix.of(2, 3), HilbertMatrix.of(3, 3));
    MapThread.of(l -> ComplexScalar.I, list, 0);
    try {
      MapThread.of(l -> ComplexScalar.I, list, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
