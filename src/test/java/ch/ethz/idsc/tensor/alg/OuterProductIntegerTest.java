// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class OuterProductIntegerTest extends TestCase {
  public static OuterProductInteger create(int[] size, boolean forward) {
    return new OuterProductInteger(Arrays.copyOf(size, size.length), forward);
  }

  public void testBasic() {
    OuterProductInteger outerProductInteger = create(new int[] { 2, 3 }, true);
    int count = 0;
    for (List<Integer> list : outerProductInteger) {
      assertEquals(list.size(), 2);
      ++count;
    }
    assertEquals(count, 6);
  }

  public void testMore() {
    int count1 = 0;
    for (List<Integer> list : create(new int[] { 4, 1, 2, 3 }, false)) {
      ++count1;
      list.get(0);
    }
    int count2 = 0;
    for (List<Integer> list : create(new int[] { 4, 1, 2, 3 }, true)) {
      ++count2;
      list.get(0);
    }
    assertEquals(count1, count2);
  }
}
