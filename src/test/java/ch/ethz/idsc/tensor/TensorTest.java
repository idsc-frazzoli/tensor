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
    assertFalse(ScalarQ.of(Tensors.empty()));
  }

  public void testLength() {
    Tensor a = DoubleScalar.of(2.32123);
    assertTrue(ScalarQ.of(a));
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

  public void testAddFail() {
    try {
      Tensors.vector(1, 2, 3).add(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testExtractAsCopy() {
    Tensor mat = Array.zeros(3, 3);
    Tensor cpy = mat.copy();
    Tensor ref = mat.extract(1, 3);
    ref.set(entry -> Tensors.vector(1, 2), 1, 1);
    assertEquals(mat, cpy);
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

  public void testExtractFail() {
    Tensors.vector(1, 2, 3, 4, 5, 6).extract(3, 6);
    Tensors.vector(1, 2, 3, 4, 5, 6).extract(6, 6);
    try {
      Tensors.vector(1, 2, 3, 4, 5, 6).extract(3, 7);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Tensors.vector(1, 2, 3, 4, 5, 6).extract(7, 6);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBlock() {
    Tensor a = Tensors.vector(1, 2, 3, 4, 5, 6);
    assertEquals(a.block(Arrays.asList(2), Arrays.asList(2)), Tensors.vector(3, 4));
    try {
      a.block(Arrays.asList(1), Arrays.asList(2, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarStream() {
    List<Tensor> asd = Arrays.asList(RealScalar.ZERO, RealScalar.of(3));
    Tensor a = Tensor.of(asd.stream());
    assertEquals(a.length(), 2);
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
}
