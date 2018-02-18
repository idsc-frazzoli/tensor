// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TotalTest extends TestCase {
  public void testTotal() {
    Tensor a = Tensors.vectorLong(7, 2);
    Tensor b = Tensors.vectorLong(3, 4);
    Tensor c = Tensors.vectorLong(2, 2);
    Tensor d = Tensors.of(a, b, c);
    assertEquals(Total.of(d), Tensors.vectorLong(12, 8));
    Tensor e = Tensors.vectorLong(0, 2, 6);
    assertEquals(Total.of(e), DoubleScalar.of(2 + 6));
    assertEquals(Total.of(Tensors.empty()), DoubleScalar.of(0));
    assertEquals(DoubleScalar.of(0), Total.of(Tensors.empty()));
  }

  public void testAddEmpty() {
    Tensor a = Tensors.of(Tensors.empty());
    Tensor b = Total.of(a);
    assertEquals(b, Tensors.empty());
  }

  public void testExample() {
    Tensor tensor = Total.of(Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}}"));
    assertEquals(tensor, Tensors.vector(9, 12));
  }

  public void testPmulEmpty() {
    Tensor a = Tensors.of(Tensors.empty());
    Tensor b = Total.prod(a);
    assertEquals(b, Tensors.empty());
    assertEquals(RealScalar.of(1), Total.prod(Tensors.empty()));
  }

  public void testPmul1() {
    Tensor a = Tensors.vectorLong(1, 2, 2, 5, 1);
    Tensor r = Total.prod(a);
    assertEquals(r, RealScalar.of(20));
  }

  public void testPmul2() {
    Tensor a = Tensors.matrix(new Number[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
    Tensor r = Total.prod(a);
    assertEquals(r, Tensors.vector(15, 48));
  }

  public void testPmul3() {
    Tensor a = Tensors.matrix(new Number[][] { { 1., 2, 3 }, { 4, 5., 6 } });
    Tensor r = Total.prod(a);
    assertEquals(r, Tensors.vector(4, 10, 18));
  }

  public void testTotalScalarFail() {
    try {
      Total.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testTotalProdFail() {
    try {
      Total.prod(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
