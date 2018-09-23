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
  public void testDocumentation() {
    assertEquals(ArgMax.of(Tensors.vector(3, 4, 2, 0, 3)), 1);
    assertEquals(ArgMax.of(Tensors.vector(4, 3, 2, 4, 3)), 0);
  }

  public void testConvention() {
    assertEquals(ArgMin.EMPTY, -1);
    assertEquals(ArgMax.EMPTY, -1);
  }

  public void testEmpty1() {
    assertEquals(ArgMin.EMPTY, ArgMin.of(Tensors.empty(), null));
    assertEquals(ArgMax.EMPTY, ArgMax.of(Tensors.empty(), null));
  }

  public void testEmpty2() {
    assertEquals(ArgMin.of(Tensors.empty()), ArgMin.EMPTY);
    assertEquals(ArgMax.of(Tensors.empty()), ArgMax.EMPTY);
  }

  public void testMax() {
    assertEquals(4, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100)));
    assertEquals(3, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8)));
  }

  public void testMaxComparatorIncr() {
    assertEquals(4, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100), Comparators.increasing()));
    assertEquals(3, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8), Comparators.increasing()));
  }

  public void testMaxComparatorDecr() {
    assertEquals(1, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100), Comparators.decreasing()));
    assertEquals(5, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8, 0), Comparators.decreasing()));
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
