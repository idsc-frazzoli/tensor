// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class TensorObjectTest extends TestCase {
  public void testHash() {
    Tensor a = Tensors.vectorLong(7, 2);
    Tensor b = Tensors.vectorLong(7, 2);
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertTrue(a.hashCode() != 0);
  }

  public void testHashDifferent() {
    Tensor a = Tensors.vectorLong(7, 2);
    Tensor b = Tensors.vectorLong(722, 18275);
    assertFalse(a.hashCode() == b.hashCode());
  }

  public void testHashCopy() {
    Tensor a = Tensors.of(Tensors.vectorLong(2, -81, 7, 2, 8), Tensors.vector(32, 3.123));
    Tensor b = a.copy();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  public void testHashScalar() {
    Tensor c = DoubleScalar.of(3.14);
    Tensor d = DoubleScalar.of(3.14);
    assertEquals(c, d);
    assertEquals(c.hashCode(), d.hashCode());
  }

  public void testEquals() {
    Tensor a = DoubleScalar.of(1.23);
    assertEquals(a, DoubleScalar.of(1.23));
    assertTrue(!a.equals(DoubleScalar.of(-1.23)));
    Tensor b = Tensors.vectorLong(3, 4, 5);
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
    Tensor c = Tensors.vectorLong(3, 4, 5);
    assertEquals(b, c);
    Tensor d = Tensors.of(Tensors.vectorLong(1, 2), a);
    Tensor e = Tensors.of(Tensors.vectorLong(1, 2), DoubleScalar.of(1.23));
    assertEquals(d, e);
    Tensor f = Tensors.of(Tensors.vectorLong(1, 2), DoubleScalar.of(-1.23));
    assertFalse(d.equals(f));
  }

  public void testEmptyEquals() {
    Tensor empty = Tensors.empty();
    assertFalse(empty.equals(null));
    assertFalse(empty.equals(Integer.valueOf(123)));
    assertFalse(empty.equals(RealScalar.of(2)));
  }

  public void testNotEquals() {
    assertFalse(Array.zeros(3, 2).equals(Array.zeros(2, 3)));
    assertFalse(Array.zeros(3, 2).equals(Array.zeros(3, 3)));
  }

  public void testToString() {
    assertEquals(Tensors.vector(2, 3, 4).toString(), "{2, 3, 4}");
  }
}
