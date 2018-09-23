// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Comparators;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class SortTest extends TestCase {
  public void testSort() {
    assertEquals(Sort.of(Tensors.vector(0, 4, 5, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    assertEquals(Sort.of(Tensors.vector(4, 5, 0, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    final Tensor m = Tensors.vectorDouble(.4, 0, .5, .2, -.3);
    assertEquals(Sort.of(m), Tensors.vectorDouble(-.3, 0, .2, .4, .5));
    assertEquals(Sort.of(m, Comparators.decreasing()), Tensors.vectorDouble(.5, .4, .2, 0, -.3));
    assertEquals(Sort.of(m), Sort.of(m, Comparators.increasing()));
  }

  public void testSortRows() {
    Comparator<Tensor> comparator = new Comparator<Tensor>() {
      @Override
      public int compare(Tensor o1, Tensor o2) {
        return Scalars.compare(o1.Get(0), o2.Get(0));
      }
    };
    Tensor a = Tensors.fromString("{{4,1},{2,8},{9,0},{3,5}}");
    Tensor s = Sort.of(a, comparator);
    assertEquals(s, Tensors.fromString("{{2, 8}, {3, 5}, {4, 1}, {9, 0}}"));
  }

  public void testStrings() {
    Tensor vector = Tensors.of( //
        StringScalar.of("c"), //
        StringScalar.of("a"), //
        StringScalar.of("b"));
    assertEquals(Sort.of(vector).toString(), "{a, b, c}");
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(-3, "m");
    Scalar qs2 = Quantity.of(2, "m");
    Tensor vec = Tensors.of(qs2, qs1);
    assertEquals(Sort.of(vec), Tensors.of(qs1, qs2));
  }

  public void testQuantity2() {
    Tensor vector = Tensors.of( //
        Quantity.of(0, "m"), Quantity.of(9, "m"), //
        Quantity.of(-3, "m"), Quantity.of(0, "s"), RealScalar.ZERO);
    try {
      Sort.of(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      Sort.of(RealScalar.of(3.12));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
