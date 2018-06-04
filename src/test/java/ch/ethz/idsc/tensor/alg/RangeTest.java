// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class RangeTest extends TestCase {
  public void testRange() {
    Tensor t = Range.of(Integer.MAX_VALUE, Integer.MAX_VALUE + 4L);
    Tensor r = Tensors.fromString("{2147483647, 2147483648, 2147483649, 2147483650}");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testRange2() {
    Tensor t = Range.of(2, 7);
    Tensor r = Tensors.vector(2, 3, 4, 5, 6);
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testRangeEmpty() {
    assertEquals(Range.of(6, 6), Tensors.empty());
    assertEquals(Range.of(6, 5), Tensors.empty());
  }
}
