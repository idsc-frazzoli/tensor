// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesTest extends TestCase {
  public void testByteArray() {
    Tensor tensor = Tensors.fromString("{{1,2,3},-1,{{256}}}");
    byte[] array = Primitives.toByteArray(tensor);
    assertEquals(array[0], 1);
    assertEquals(array[1], 2);
    assertEquals(array[3], -1);
    assertEquals(array[4], 0);
    assertEquals(array.length, 5);
  }
}
