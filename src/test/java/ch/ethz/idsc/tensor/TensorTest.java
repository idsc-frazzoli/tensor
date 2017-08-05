// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Numel;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class TensorTest extends TestCase {
  public void testConstantAll() {
    assertTrue(Tensor.ALL < -1000000);
  }

  public void testIsScalar() {
    assertFalse(Tensors.empty().isScalar());
  }

  public void testLength() {
    Tensor a = DoubleScalar.of(2.32123);
    assertTrue(a.isScalar());
    assertEquals(a.length(), Scalar.LENGTH);
    Tensor b = Tensors.vectorLong(3, 2);
    assertEquals(b.length(), 2);
    Tensor c = DoubleScalar.of(1.23);
    assertEquals(c.length(), Scalar.LENGTH);
    Tensor d = Tensors.empty();
    assertEquals(d.length(), 0);
    Tensor e = Tensors.of(a, b, c);
    assertEquals(e.length(), 3);
  }

  private static Scalar incr(Scalar a) {
    return a.add(RealScalar.ONE);
  }

  public void testSet() {
    Tensor a = Tensors.matrixInt( //
        new int[][] { { 3, 4 }, { 1, 2 }, { 9, 8 } });
    a.set(TensorTest::incr, 0, 0);
    a.set(TensorTest::incr, 1, 0);
    a.set(TensorTest::incr, 2, 1);
    Tensor b = Tensors.matrixInt( //
        new int[][] { { 4, 4 }, { 2, 2 }, { 9, 9 } });
    assertEquals(a, b);
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
        Tensors.fromString("{3,-1,-1}"));
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
    Tensor r = Tensors.fromString("{{3, 8, 18}, {-27, 2, 2}}");
    assertEquals(a.pmul(c), r);
  }

  public void testPMulFail() {
    try {
      Tensors.vector(1, 2, 3).pmul(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAddFail() {
    try {
      Tensors.vector(1, 2, 3).add(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAppend() {
    Tensor a0 = RealScalar.of(3);
    Tensor a1 = Tensors.empty();
    Tensor tensor = Tensors.empty();
    tensor.append(a0);
    tensor.append(a1);
    assertEquals(tensor, Tensors.of(a0, a1));
  }

  public void testAppend2() {
    Tensor a0 = Array.of(l -> Tensors.empty(), 5);
    a0.set(t -> t.append(RealScalar.of(0)), 1);
    a0.set(t -> t.append(RealScalar.of(1)), 3);
    a0.set(t -> t.append(RealScalar.of(2)), 1);
    assertEquals(a0.length(), 5);
    assertEquals(Numel.of(a0), 3);
    assertEquals(a0.get(1), Tensors.vector(0, 2));
  }

  public void testUnmodifiable() {
    Tensor tensor = Tensors.vector(3, 4, 5, 6, -2);
    tensor.set(DoubleScalar.of(.3), 2);
    Tensor unmodi = tensor.unmodifiable();
    assertEquals(tensor, unmodi);
    try {
      unmodi.set(DoubleScalar.of(.3), 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unmodi.append(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unmodi.set(t -> t.append(RealScalar.ZERO));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    Tensor dot = unmodi.dot(unmodi);
    assertEquals(dot, DoubleScalar.of(65.09));
    assertEquals(DoubleScalar.of(65.09), dot);
  }

  public void testUnmodifiable2() {
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 2 }, { 3, 4 } }).unmodifiable();
    Tensor mc = m.copy();
    m.get(1).set(RealScalar.ZERO, 1);
    assertEquals(m, mc);
  }

  public void testAdd() {
    Tensor c = Tensors.vectorLong(1, 2, 6);
    Tensor d = Tensors.vectorLong(3, 4, 5);
    assertTrue(c.add(d).equals(d.add(c)));
    Tensor e = Tensors.vectorLong(4, 6, 11);
    assertTrue(c.add(d).equals(e));
  }

  public void testAdd2() {
    Tensor A = Tensors.matrixInt(new int[][] { {}, { 1, -2, 3 }, { 4, 9 } });
    Tensor B = Tensors.matrixInt(new int[][] { {}, { 0, 2, 2 }, { 8, -7 } });
    Tensor expected = Tensors.fromString("{{}, {1, 0, 5}, {12, 2}}");
    assertEquals(expected, A.add(B));
  }

  public void testDotEmpty() {
    Tensor a = Tensors.empty().dot(Tensors.empty());
    assertTrue(a.isScalar());
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

  public void testHashUnmod() {
    Tensor a = Tensors.of(Tensors.vectorLong(2, -81, 7, 2, 8), Tensors.vector(32, 3.123));
    Tensor b = a.unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  public void testHashScalar() {
    Tensor c = DoubleScalar.of(3.14);
    Tensor d = DoubleScalar.of(3.14);
    assertEquals(c, d);
    assertEquals(c.hashCode(), d.hashCode());
  }

  public void testScalarStream() {
    List<Scalar> asd = Arrays.asList(RealScalar.ZERO, RealScalar.of(3));
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
    Tensor b = a.map(Chop._12);
    assertEquals(b, Tensors.of(RealScalar.ZERO, Tensors.of(RealScalar.ZERO)));
  }

  public void testToString() {
    assertEquals(Tensors.vector(2, 3, 4).toString(), "{2, 3, 4}");
  }
}
