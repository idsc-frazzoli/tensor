// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class RangeTest extends TestCase {
  public void testRange() {
    Tensor t = Range.of(5);
    Tensor r = Tensors.vector(0, 1, 2, 3, 4);
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testRange2() {
    Tensor t = Range.of(2, 7);
    Tensor r = Tensors.vector(2, 3, 4, 5, 6);
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }
}
