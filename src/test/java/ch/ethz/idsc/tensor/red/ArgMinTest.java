// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Comparators;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ArgMinTest extends TestCase {
  public void testDocumentation() {
    assertEquals(ArgMin.of(Tensors.vector(3, 4, 1, 2, 3)), 2);
    assertEquals(ArgMin.of(Tensors.vector(1, 4, 1, 2, 3)), 0);
  }

  public void testEmpty() {
    assertEquals(-1, ArgMin.of(Tensors.empty(), null));
    assertEquals(-1, ArgMax.of(Tensors.empty(), null));
  }

  public void testMin() {
    assertEquals(1, ArgMin.of(Tensors.vectorDouble(3., .6, 8, .6, 100)));
    assertEquals(2, ArgMin.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 8)));
  }

  public void testMinComparatorIncr() {
    assertEquals(1, ArgMin.of(Tensors.vectorDouble(3., .6, 8, .6, 100), Comparators.increasing()));
    assertEquals(2, ArgMin.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 8), Comparators.increasing()));
  }

  public void testMinComparatorDecr() {
    assertEquals(4, ArgMin.of(Tensors.vectorDouble(3., .6, 8, .6, 100), Comparators.decreasing()));
    assertEquals(3, ArgMin.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 8), Comparators.decreasing()));
  }

  public void testScalar() {
    try {
      ArgMin.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      ArgMin.of(HilbertMatrix.of(6));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
