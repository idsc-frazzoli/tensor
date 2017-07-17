// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Comparators;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ArgMaxTest extends TestCase {
  public void testEmpty() {
    assertEquals(-1, ArgMin.of(Tensors.empty(), null));
    assertEquals(-1, ArgMax.of(Tensors.empty(), null));
  }

  public void testMax() {
    assertEquals(4, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100)));
    assertEquals(3, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8)));
  }

  public void testMaxComparator() {
    assertEquals(4, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100), Comparators.increasing()));
    assertEquals(3, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8), Comparators.increasing()));
  }

  public void testInf() {
    Scalar inf = RealScalar.of(Double.POSITIVE_INFINITY);
    Tensor vec = Tensors.of(RealScalar.ZERO, inf, inf);
    int pos = ArgMax.of(vec);
    assertEquals(pos, 1);
  }

  public void testScalar() {
    try {
      ArgMax.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      ArgMax.of(HilbertMatrix.of(6));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
