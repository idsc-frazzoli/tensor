// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ObjectFormatTest extends TestCase {
  public void testSome() throws Exception {
    Tensor inp = Tensors.fromString("[1,[2,3,[4.3]],1]");
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }

  public void testNull() throws Exception {
    Tensor inp = null;
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }
}
