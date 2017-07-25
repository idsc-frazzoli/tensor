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

  public void testMore() {
    int count1 = 0;
    for (List<Integer> list : OuterProductInteger.of(new int[] { 4, 1, 2, 3 }, false)) {
      ++count1;
      list.get(0);
    }
    int count2 = 0;
    for (List<Integer> list : OuterProductInteger.of(new int[] { 4, 1, 2, 3 }, true)) {
      ++count2;
      list.get(0);
    }
    assertEquals(count1, count2);
  }
}
