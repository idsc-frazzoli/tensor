// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
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
    assertEquals(4, ArgMax.of(Tensors.vectorDouble(3., .6, 8, .6, 100), RealScalar.comparatorAscending));
    assertEquals(3, ArgMax.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 0, 8), RealScalar.comparatorAscending));
  }
}
