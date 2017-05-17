// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Comparators;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SortTest extends TestCase {
  public void testSort() {
    assertEquals(Sort.of(Tensors.vector(0, 4, 5, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    assertEquals(Sort.of(Tensors.vector(4, 5, 0, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    final Tensor m = Tensors.vectorDouble(.4, 0, .5, .2, -.3);
    assertEquals(Sort.of(m), Tensors.vectorDouble(-.3, 0, .2, .4, .5));
    assertEquals(Sort.of(m, Comparators.descending()), Tensors.vectorDouble(.5, .4, .2, 0, -.3));
    assertEquals(Sort.of(m), Sort.of(m, Comparators.ascending()));
  }

  static Comparator<Tensor> FIRSTENTRYCOMPARATOR = new Comparator<Tensor>() {
    @Override
    public int compare(Tensor o1, Tensor o2) {
      return Scalars.compare(o1.Get(0), o2.Get(0));
    }
  };

  public void testSortRows() {
    Tensor a = Tensors.fromString("{{4,1},{2,8},{9,0},{3,5}}");
    // System.out.println(Pretty.of(a));
    Tensor s = Sort.of(a, FIRSTENTRYCOMPARATOR);
    // System.out.println(s);
    // System.out.println(Pretty.of(s));
    assertEquals(s, Tensors.fromString("{{2, 8}, {3, 5}, {4, 1}, {9, 0}}"));
  }
}
