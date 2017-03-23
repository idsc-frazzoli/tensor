// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalarComparators;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ArgMinTest extends TestCase {
  public void testEmpty() {
    assertEquals(-1, ArgMin.of(Tensors.empty(), null));
    assertEquals(-1, ArgMax.of(Tensors.empty(), null));
  }

  public void testMin() {
    assertEquals(1, ArgMin.of(Tensors.vectorDouble(3., .6, 8, .6, 100)));
    assertEquals(2, ArgMin.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 8)));
  }

  public void testMinComparator() {
    assertEquals(1, ArgMin.of(Tensors.vectorDouble(3., .6, 8, .6, 100), RealScalarComparators.ASCENDING));
    assertEquals(2, ArgMin.of(Tensors.vectorDouble(3, 3., .6, 8, .6, 8), RealScalarComparators.ASCENDING));
  }
}
