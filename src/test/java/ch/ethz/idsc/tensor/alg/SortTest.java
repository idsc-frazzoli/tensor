// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Collections;
import java.util.Comparator;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class SortTest extends TestCase {
  public void testSort() {
    assertEquals(Sort.of(Tensors.vector(0, 4, 5, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    assertEquals(Sort.of(Tensors.vector(4, 5, 0, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    final Tensor m = Tensors.vectorDouble(0.4, 0, 0.5, 0.2, -0.3);
    assertEquals(Sort.of(m), Tensors.vectorDouble(-0.3, 0, 0.2, 0.4, 0.5));
    assertEquals(Sort.of(m, Collections.reverseOrder()), Tensors.vectorDouble(0.5, 0.4, 0.2, 0, -0.3));
    assertEquals(Sort.ofTensor(m.unmodifiable(), Collections.reverseOrder()), Tensors.vectorDouble(0.5, 0.4, 0.2, 0, -0.3));
    assertEquals(Sort.of(m), Sort.of(m));
    assertEquals(Sort.of(m.unmodifiable()), Sort.of(m));
  }

  public void testSortRows() {
    Comparator<Tensor> comparator = new Comparator<Tensor>() {
      @Override
      public int compare(Tensor o1, Tensor o2) {
        return Scalars.compare(o1.Get(0), o2.Get(0));
      }
    };
    Tensor a = Tensors.fromString("{{4, 1}, {2, 8}, {9, 0}, {3, 5}}");
    Tensor s = Sort.ofTensor(a, comparator);
    assertEquals(s, Tensors.fromString("{{2, 8}, {3, 5}, {4, 1}, {9, 0}}"));
  }

  public void testStrings() {
    Tensor vector = Tensors.of( //
        StringScalar.of("c"), //
        StringScalar.of("a"), //
        StringScalar.of("b"));
    assertEquals(Sort.of(vector).toString(), "{a, b, c}");
    assertEquals(Sort.of(vector.unmodifiable()).toString(), "{a, b, c}");
  }

  public void testStringScalar() {
    Comparator<GaussScalar> comparator = new Comparator<GaussScalar>() {
      @Override
      public int compare(GaussScalar o1, GaussScalar o2) {
        return o1.prime().compareTo(o2.prime());
      }
    };
    Scalar qs1 = GaussScalar.of(-3, 7);
    Scalar qs2 = GaussScalar.of(-3, 17);
    Tensor vec = Tensors.of(qs2, qs1);
    assertEquals(Sort.of(vec, comparator), Tensors.of(qs1, qs2));
    assertEquals(Sort.of(vec.unmodifiable(), comparator), Tensors.of(qs1, qs2));
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(-3, "m");
    Scalar qs2 = Quantity.of(2, "m");
    Tensor vec = Tensors.of(qs2, qs1);
    assertEquals(Sort.of(vec), Tensors.of(qs1, qs2));
    assertEquals(Sort.of(vec, Collections.reverseOrder()), Tensors.of(qs2, qs1));
  }

  public void testQuantity2() {
    Tensor vector = Tensors.of( //
        Quantity.of(0, "m"), Quantity.of(9, "m"), //
        Quantity.of(-3, "m"), Quantity.of(0, "s"), RealScalar.ZERO);
    try {
      Sort.of(vector);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testReference() {
    Tensor tensor = Tensors.fromString("{{1, 2, 3}}");
    Tensor sorted = Sort.of(tensor);
    assertEquals(tensor, sorted);
    tensor.set(RealScalar.ONE::add, Tensor.ALL, Tensor.ALL);
    assertFalse(tensor.equals(sorted));
    assertEquals(sorted, Tensors.fromString("{{1, 2, 3}}"));
    assertEquals(tensor, Tensors.fromString("{{2, 3, 4}}"));
  }

  public void testSortEmpty() {
    assertEquals(Sort.of(Tensors.empty()), Tensors.empty());
    assertEquals(Sort.of(Tensors.empty(), Collections.reverseOrder()), Tensors.empty());
    assertEquals(Sort.ofTensor(Tensors.empty(), Collections.reverseOrder()), Tensors.empty());
  }

  public void testScalarFail() {
    try {
      Sort.of(RealScalar.of(3.12));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarVectorFail() {
    try {
      Sort.of(Tensors.vector(1, 2, 3), null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
