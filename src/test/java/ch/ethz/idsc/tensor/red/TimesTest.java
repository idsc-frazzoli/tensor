// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.num.GaussScalar;
import junit.framework.TestCase;

public class TimesTest extends TestCase {
  public void testSingle() {
    assertEquals(Times.of(RealScalar.of(3)), RealScalar.of(3));
  }

  public void testProduct() {
    assertEquals(Times.of(RealScalar.of(3), RealScalar.of(8)), RealScalar.of(3 * 8));
    assertEquals(Times.of(RealScalar.of(3), RealScalar.of(8), RealScalar.of(-4)), RealScalar.of(3 * 8 * -4));
  }

  public void testEmpty() {
    assertEquals(Times.of(), RealScalar.ONE);
  }

  public void testGaussScalar() {
    GaussScalar a = GaussScalar.of(3, 7);
    GaussScalar b = GaussScalar.of(4, 7);
    assertEquals(Times.of(a, b), GaussScalar.of(12, 7));
  }

  public void testPmulEmpty() {
    Tensor a = Tensors.of(Tensors.empty());
    Tensor b = Times.pmul(a);
    assertEquals(b, Tensors.empty());
    assertEquals(RealScalar.of(1), Times.pmul(Tensors.empty()));
  }

  public void testPmul1() {
    Tensor a = Tensors.vectorLong(1, 2, 2, 5, 1);
    Tensor r = Times.pmul(a);
    assertEquals(r, RealScalar.of(20));
  }

  public void testPmul2() {
    Tensor a = Tensors.matrix(new Number[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
    Tensor r = Times.pmul(a);
    assertEquals(r, Tensors.vector(15, 48));
  }

  public void testPmul3() {
    Tensor a = Tensors.matrix(new Number[][] { { 1., 2, 3 }, { 4, 5., 6 } });
    Tensor r = Times.pmul(a);
    assertEquals(r, Tensors.vector(4, 10, 18));
  }

  public void testTotalProdFail() {
    try {
      Times.pmul(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Times.of(RealScalar.of(3), null, RealScalar.of(8));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
