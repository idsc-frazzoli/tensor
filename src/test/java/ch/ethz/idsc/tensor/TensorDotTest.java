// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class TensorDotTest extends TestCase {
  public void testDotEmpty() {
    Tensor a = Tensors.empty().dot(Tensors.empty());
    assertTrue(ScalarQ.of(a));
    assertEquals(a, RealScalar.ZERO);
    assertEquals(a, DoubleScalar.of(0));
    assertEquals(RealScalar.ZERO, a);
    assertEquals(DoubleScalar.of(0), a);
  }

  public void testDot2() {
    Tensor tensor = Tensors.of(Tensors.empty());
    Tensor sca = tensor.dot(Tensors.empty());
    assertEquals(sca, Tensors.vectorDouble(0));
  }

  public void testDot3() {
    Tensor tensor = Tensors.of(Tensors.empty(), Tensors.empty());
    Tensor sca = tensor.dot(Tensors.empty());
    assertEquals(sca, Tensors.vectorLong(0, 0));
  }

  public void testDot4() {
    Tensor c = Tensors.vectorLong(1, 2, 6);
    Tensor d = Tensors.vectorLong(3, 4, 5);
    assertTrue(c.dot(d) instanceof RationalScalar);
    assertEquals(c.dot(d), RationalScalar.of(3 + 8 + 30, 1));
  }

  public void testDot5() {
    Tensor c = Tensors.vectorDouble(1, 2, 6.);
    Tensor d = Tensors.vectorLong(3, 4, 5);
    assertTrue(c.dot(d) instanceof DoubleScalar);
    assertEquals(c.dot(d), RationalScalar.of(3 + 8 + 30, 1));
  }

  public void testDot6() {
    Tensor a = Tensors.vectorLong(7, 2);
    Tensor b = Tensors.vectorLong(3, 4);
    Tensor c = Tensors.vectorLong(2, 2);
    Tensor d = Tensors.of(a, b, c);
    Tensor e = Tensors.vectorLong(-1, 1);
    Tensor f = d.dot(e);
    Tensor g = Tensors.vectorLong(-7 + 2, -3 + 4, -2 + 2);
    assertEquals(f, g);
  }

  public void testDotIrregular() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.fromString("{{1,{2}},{2,{3}},{4,{5}}}");
    assertEquals(a.dot(b), Tensors.fromString("{17, {23}}"));
  }
}
