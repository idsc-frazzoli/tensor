// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.ArrayQ;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class TensorGetSetTest extends TestCase {
  public void testGetEmpty() {
    assertEquals(Tensors.empty().get(), Tensors.empty());
    assertEquals(Tensors.empty().get(Arrays.asList()), Tensors.empty());
    assertEquals(Array.zeros(2, 3).get(), Array.zeros(2, 3));
    assertEquals(Array.zeros(2, 3).get(Arrays.asList()), Array.zeros(2, 3));
  }

  public void testGet() {
    Tensor a = Tensors.matrixInt( //
        new int[][] { { 3, 4 }, { 1, 2 }, { 9, 8 } });
    assertEquals(a.get(0, 0), Tensors.fromString("3"));
    assertEquals(a.get(1, 1), Tensors.fromString("2"));
    assertEquals(a.get(0), Tensors.fromString("{3, 4}"));
    assertEquals(a.get(1), Tensors.fromString("{1, 2}"));
    assertEquals(a.get(2), Tensors.fromString("{9, 8}"));
    assertEquals(a.get(Tensor.ALL, 0), Tensors.fromString("{3, 1, 9}"));
    assertEquals(a.get(Tensor.ALL, 1), Tensors.fromString("{4, 2, 8}"));
  }

  public void testGetAllSimple() {
    Tensor a = Array.zeros(3, 4);
    Tensor c = a.get(Tensor.ALL, 2);
    c.set(RealScalar.ONE, 1);
    assertEquals(a, Array.zeros(3, 4));
  }

  public void testGetAll1() {
    Tensor a = Array.zeros(3).unmodifiable();
    a.get().set(RealScalar.ONE, 1);
    // a.get(1).set(RealScalar.ONE, 1); // scalar is immutable
    Tensor b = a.get(Tensor.ALL);
    b.set(Tensors.vector(1, 2, 3), Tensor.ALL);
    assertEquals(b, Tensors.vector(1, 2, 3));
    assertEquals(a, Array.zeros(3));
  }

  public void testGetAll2() {
    Tensor a = Array.zeros(3, 4).unmodifiable();
    a.get().set(RealScalar.ONE, 1);
    a.get(1).set(RealScalar.ONE, 1);
    a.get(Tensor.ALL).set(Tensors.vector(1, 2, 3), Tensor.ALL);
    a.get(Tensor.ALL, 2).set(RealScalar.ONE, 1);
    a.get(2, Tensor.ALL).set(Tensors.vector(1, 2, 3, 4), Tensor.ALL);
    a.get(Tensor.ALL, Tensor.ALL).set(Array.of(l -> RealScalar.ONE, 3, 4), Tensor.ALL, Tensor.ALL);
    assertEquals(a, Array.zeros(3, 4));
  }

  public void testGetAll3() {
    Tensor a = Array.zeros(3, 4, 3).unmodifiable();
    a.get().set(RealScalar.ONE, 1);
    a.get(1).set(RealScalar.ONE, 1);
    a.get(Tensor.ALL).set(Tensors.vector(1, 2, 3), Tensor.ALL);
    a.get(Tensor.ALL, 2).set(RealScalar.ONE, 1);
    a.get(2, Tensor.ALL).set(Tensors.vector(1, 2, 3, 4), Tensor.ALL);
  }

  public void testSetNotByRef() {
    Tensor a = Tensors.vector(1);
    Tensor row = Tensors.vector(1, 2, 3, 4);
    a.set(row, 0);
    Tensor copy = a.copy();
    row.set(s -> s.multiply(RealScalar.ZERO), 2);
    assertEquals(a, copy);
  }

  public void testSetAllLast() {
    Tensor a = Tensors.vector(0, 1, 3, 4);
    Tensor row = Tensors.vector(5, 6, 7, 8);
    a.set(row, Tensor.ALL);
    assertEquals(a, row);
  }

  public void testSetMultiAll0() {
    Tensor a = Array.zeros(2, 3);
    Tensor b = Array.zeros(2, 1);
    a.set(b, Tensor.ALL, 2);
    Tensor c = Tensors.fromString("{{0, 0, {0}}, {0, 0, {0}}}");
    assertEquals(a, c);
  }

  public void testSetMultiAll1() {
    Tensor a = Array.zeros(2, 3, 4, 5);
    Tensor b = Array.zeros(3, 5, 1);
    a.set(b, 1, Tensor.ALL, 2, Tensor.ALL);
    assertFalse(ArrayQ.of(a));
    List<Integer> dims = Dimensions.of(a.get(1, Tensor.ALL, 2, Tensor.ALL));
    assertEquals(dims, Dimensions.of(b));
  }

  public void testSetMultiAll2() {
    Tensor a = Array.zeros(2, 3, 4, 5);
    Tensor b = Array.zeros(2, 4, 1);
    a.set(b, Tensor.ALL, 2, Tensor.ALL, 2);
    assertFalse(ArrayQ.of(a));
    List<Integer> dims = Dimensions.of(a.get(Tensor.ALL, 2, Tensor.ALL, 2));
    assertEquals(dims, Dimensions.of(b));
  }

  public void testSetAllFail() {
    Tensor a = Tensors.vector(0, 1, 3, 4);
    Tensor c = a.copy();
    try {
      a.set(Tensors.vector(5, 6, 7, 8, 9), Tensor.ALL);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    assertEquals(a, c);
    try {
      a.set(Tensors.vector(5, 6, 7), Tensor.ALL);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    assertEquals(a, c);
  }

  public void testSet333() {
    Tensor ad = LieAlgebras.sl3();
    Tensor mat = HilbertMatrix.of(3);
    ad.set(mat, Tensor.ALL, 2);
    assertEquals(Dimensions.of(ad), Arrays.asList(3, 3, 3));
    assertEquals(ad.get(Tensor.ALL, 2), mat);
  }

  public void testSetSelf() {
    Tensor a = Tensors.vector(1); // 1
    assertTrue(TensorRank.of(a) == 1);
    a.set(a, 0);
    assertTrue(TensorRank.of(a) == 2);
    a.set(a, Tensor.ALL);
    assertTrue(TensorRank.of(a) == 2);
    a.set(a, 0);
    assertTrue(TensorRank.of(a) == 3);
    a.set(a, Tensor.ALL);
    a.set(a, 0);
    a.set(a, 0);
    assertTrue(TensorRank.of(a) == 5);
  }

  public void testSetFunctionAllLast() {
    Tensor a = Tensors.vector(0, 1, 3, 4, 9);
    a.set(Increment.ONE, Tensor.ALL);
    Tensor b = Tensors.vector(1, 2, 4, 5, 10);
    assertEquals(a, b);
  }

  public void testSetAll() {
    Tensor matrix = HilbertMatrix.of(4, 6);
    Tensor column = Tensors.vector(3, 2, 1, 0);
    matrix.set(column, Tensor.ALL, 2);
    assertEquals(matrix.get(Tensor.ALL, 2), column);
  }

  public void testSetFunctionAll() {
    Tensor matrix = HilbertMatrix.of(4, 6);
    Tensor column = Tensors.vector(3, 4, 5, 6);
    matrix.set(Scalar::reciprocal, Tensor.ALL, 2);
    assertEquals(matrix.get(Tensor.ALL, 2), column);
  }

  public void testSetTensorLevel0() {
    Tensor vector = Tensors.vector(2, 3, 4);
    try {
      vector.set(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSetFunctionLevel0() {
    Tensor vector = Tensors.vector(2, 3, 4);
    try {
      vector.set(t -> RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
