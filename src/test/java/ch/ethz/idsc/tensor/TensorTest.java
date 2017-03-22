// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Numel;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class TensorTest extends TestCase {
  public void testLength() {
    Tensor a = DoubleScalar.of(2.32123);
    assertEquals(a.length(), -1);
    Tensor b = Tensors.vectorLong(3, 2);
    assertEquals(b.length(), 2);
    Tensor c = DoubleScalar.of(1.23);
    assertEquals(c.length(), -1);
    Tensor d = Tensors.empty();
    assertEquals(d.length(), 0);
    Tensor e = Tensors.of(a, b, c);
    assertEquals(e.length(), 3);
  }

  public void testGet() {
    Tensor a = Tensors.matrixInt( //
        new int[][] { { 3, 4 }, { 1, 2 }, { 9, 8 } });
    assertEquals(a.get(0, 0), Tensors.fromString("3"));
    assertEquals(a.get(1, 1), Tensors.fromString("2"));
    assertEquals(a.get(0), Tensors.fromString("[3, 4]"));
    assertEquals(a.get(1), Tensors.fromString("[1, 2]"));
    assertEquals(a.get(2), Tensors.fromString("[9, 8]"));
    assertEquals(a.get(-1, 0), Tensors.fromString("[3, 1, 9]"));
    assertEquals(a.get(-1, 1), Tensors.fromString("[4, 2, 8]"));
  }

  public void testFlatten() {
    Tensor a = DoubleScalar.of(1.23);
    Tensor b = Tensors.vectorLong(3, 2, 3, 5);
    Tensor c = Tensors.vectorLong(3, 2, 5);
    Tensor f = Tensors.of(a, b, c);
    assertEquals(Numel.of(f), 8);
  }

  public void testFlattenN() {
    Tensor a = Tensors.vectorLong(1, 2);
    Tensor b = Tensors.vectorLong(3, 4, 5);
    Tensor c = Tensors.vectorLong(6);
    Tensor d = Tensors.of(a, b, c);
    Tensor e = Tensors.of(a, b);
    Tensor f = Tensors.of(d, e);
    assertEquals(f.flatten(0).count(), 2);
    assertEquals(f.flatten(1).count(), 5);
  }

  public void testPMul1() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(1, 2, 3), //
        Tensors.vectorLong(3, -1, -1));
    Tensor b = Tensors.vectorLong(3, 2, 2);
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 4, 6), //
        Tensors.vectorLong(9, -2, -2));
    Tensor r = Tensor.of(a.flatten(0).map(row -> row.pmul(b)));
    assertEquals(r, c);
  }

  public void testPMul2() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(new long[] { 1, 2, 3 }), //
        Tensors.fromString("[3,-1,-1]"));
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 6, 9), //
        Tensors.vectorLong(6, -2, -2));
    assertEquals(b.pmul(a), c);
  }

  public void testPMul3() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(1, 2, 3), //
        Tensors.vectorLong(3, -1, -1));
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 4, 6), //
        Tensors.vectorLong(-9, -2, -2));
    Tensor r = Tensors.fromString("[[3, 8, 18], [-27, 2, 2]]");
    assertEquals(a.pmul(c), r);
  }

  public void testAppend() {
    Tensor a0 = RealScalar.of(3);
    Tensor a1 = Tensors.empty();
    Tensor tensor = Tensors.empty();
    tensor.append(a0);
    tensor.append(a1);
    assertEquals(tensor, Tensors.of(a0, a1));
  }

  public void testUnmodifiable() {
    Tensor tensor = Tensors.vectorInt(3, 4, 5, 6, -2);
    tensor.set(DoubleScalar.of(.3), 2);
    Tensor unmodi = tensor.unmodifiable();
    assertEquals(tensor, unmodi);
    try {
      unmodi.set(DoubleScalar.of(.3), 2);
      assertTrue(false);
    } catch (Exception e) {
      // ---
    }
    try {
      unmodi.append(Tensors.empty());
      assertTrue(false);
    } catch (Exception e) {
      // ---
    }
    Tensor dot = unmodi.dot(unmodi);
    assertEquals(dot, DoubleScalar.of(65.09));
    assertEquals(DoubleScalar.of(65.09), dot);
  }

  public void testUnmodifiable2() {
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 2 }, { 3, 4 } }).unmodifiable();
    Tensor mc = m.copy();
    m.get(1).set(ZeroScalar.get(), 1);
    assertEquals(m, mc);
  }

  public void testAdd() {
    Tensor c = Tensors.vectorLong(1, 2, 6);
    Tensor d = Tensors.vectorLong(3, 4, 5);
    assertTrue(c.add(d).equals(d.add(c)));
    Tensor e = Tensors.vectorLong(4, 6, 11);
    assertTrue(c.add(d).equals(e));
  }

  public void testDot() {
    {
      Tensor a = Tensors.empty().dot(Tensors.empty());
      assertTrue(a instanceof Scalar);
      assertEquals(a, ZeroScalar.get());
      assertEquals(a, DoubleScalar.of(0));
      assertEquals(ZeroScalar.get(), a);
      assertEquals(DoubleScalar.of(0), a);
    }
    {
      Tensor tensor = Tensors.of(Tensors.empty());
      Tensor sca = tensor.dot(Tensors.empty());
      assertEquals(sca, Tensors.vectorLong(0));
    }
    {
      Tensor tensor = Tensors.of(Tensors.empty(), Tensors.empty());
      Tensor sca = tensor.dot(Tensors.empty());
      assertEquals(sca, Tensors.vectorLong(0, 0));
    }
    {
      Tensor c = Tensors.vectorLong(1, 2, 6);
      Tensor d = Tensors.vectorLong(3, 4, 5);
      assertTrue(c.dot(d) instanceof RationalScalar);
      assertEquals(c.dot(d), RationalScalar.of(3 + 8 + 30, 1));
    }
    {
      Tensor c = Tensors.vectorDouble(1, 2, 6.);
      Tensor d = Tensors.vectorLong(3, 4, 5);
      assertTrue(c.dot(d) instanceof DoubleScalar);
      assertEquals(c.dot(d), DoubleScalar.of(3 + 8 + 30));
    }
    {
      Tensor a = Tensors.vectorLong(7, 2);
      Tensor b = Tensors.vectorLong(3, 4);
      Tensor c = Tensors.vectorLong(2, 2);
      Tensor d = Tensors.of(a, b, c);
      Tensor e = Tensors.vectorLong(-1, 1);
      Tensor f = d.dot(e);
      Tensor g = Tensors.vectorLong(-7 + 2, -3 + 4, -2 + 2);
      assertEquals(f, g);
    }
  }

  public void testHash() {
    Tensor a = Tensors.vectorLong(7, 2);
    Tensor b = Tensors.vectorLong(7, 2);
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    Tensor c = DoubleScalar.of(3.14);
    Tensor d = DoubleScalar.of(3.14);
    assertEquals(c, d);
    assertEquals(c.hashCode(), d.hashCode());
  }

  public void testScalarStream() {
    List<Scalar> asd = Arrays.asList(ZeroScalar.get(), RealScalar.of(3));
    Tensor a = Tensor.of(asd.stream());
    assertEquals(a.length(), 2);
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

  public void testEquals2() {
    assertFalse(Array.zeros(3, 2).equals(Array.zeros(2, 3)));
    assertFalse(Array.zeros(3, 2).equals(Array.zeros(3, 3)));
  }

  public void testNegate() {
    Tensor a = Tensors.vectorDouble(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Tensor b = Tensors.vectorDouble(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10.0);
    assertEquals(a.negate(), b);
  }

  public void testMap() {
    Tensor a = Tensors.of(DoubleScalar.of(1e-20), Tensors.of(DoubleScalar.of(3e-19)));
    Tensor b = Chop.of(a);
    assertEquals(b, Tensors.of(ZeroScalar.get(), Tensors.of(ZeroScalar.get())));
  }
}
