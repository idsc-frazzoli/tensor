// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ObjectFormatTest extends TestCase {
  public void testSome() {
    Tensor inp = Tensors.fromString("[1,[2,3,[4.3]],1]");
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.from(bytes);
    assertEquals(inp, ten);
  }
}
