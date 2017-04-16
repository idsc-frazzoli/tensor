// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;

import junit.framework.TestCase;

public class OuterProductIntegerTest extends TestCase {
  public void testBasic() {
    OuterProductInteger asd = OuterProductInteger.forward(2, 3);
    int count = 0;
    for (List<Integer> list : asd) {
      assertEquals(list.size(), 2);
      ++count;
    }
    assertEquals(count, 6);
  }
}
