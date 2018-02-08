// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ArrayTest extends TestCase {
  public void testEmpty1() {
    assertEquals(Array.of(l -> {
      assertTrue(l.isEmpty());
      return RealScalar.ONE;
    }), RealScalar.ONE);
  }

  public void testArray() {
    Tensor table = Array.of(l -> RealScalar.of(l.get(0) * l.get(2)), 3, 2, 4);
    Tensor res = Tensors.fromString("{{{0, 0, 0, 0}, {0, 0, 0, 0}}, {{0, 1, 2, 3}, {0, 1, 2, 3}}, {{0, 2, 4, 6}, {0, 2, 4, 6}}}");
    assertEquals(table, res);
  }

  public void testCopy() {
    Tensor hilbert = HilbertMatrix.of(3, 5);
    Tensor table = Array.of(l -> hilbert.get(l), Dimensions.of(hilbert));
    assertEquals(hilbert, table);
  }

  public void testVectorBlock() {
    Tensor tensor = Tensors.vector(0, 1, 2, 3, 4, 5);
    assertEquals(tensor.block(Arrays.asList(2), Arrays.asList(2)), tensor.extract(2, 4));
  }

  private static void checkDims(Tensor tensor, List<Integer> fromIndex, List<Integer> dims) {
    Tensor array = tensor.block(fromIndex, dims);
    assertEquals(dims, Dimensions.of(array).subList(0, dims.size()));
    int rank = TensorRank.of(tensor);
    assertEquals( //
        Dimensions.of(tensor).subList(dims.size(), rank), //
        Dimensions.of(array).subList(dims.size(), rank));
  }

  public void testBlock() {
    Tensor table = Array.of(l -> Tensors.vector(l.get(0), l.get(1), l.get(2)), 3, 2, 4);
    checkDims(table, Arrays.asList(), Arrays.asList());
    checkDims(table, Arrays.asList(0), Arrays.asList(3));
    checkDims(table, Arrays.asList(2, 1), Arrays.asList(1, 1));
    checkDims(table, Arrays.asList(1, 1, 1), Arrays.asList(2, 1, 1));
    checkDims(table, Arrays.asList(2, 1, 0, 1), Arrays.asList(1, 1, 2, 1));
  }

  public void testZeros() {
    Tensor zeros = Array.zeros(3, 5, 2, 7);
    Tensor table = Array.of(l -> RealScalar.ZERO, 3, 5, 2, 7);
    assertEquals(zeros, table);
  }

  public void testZeros2() {
    Tensor zeros = Array.zeros(2, 3);
    assertEquals(zeros, Tensors.fromString("{ {  0,0, 0} ,  {0,0,0}  }"));
  }

  public void testEmpty() {
    assertEquals(Array.zeros(), RealScalar.ZERO);
    assertEquals(Array.zeros(0), Tensors.empty());
    assertEquals(Array.zeros(0, 1), Tensors.empty());
    assertEquals(Array.zeros(0, 0, 1), Tensors.empty());
    assertEquals(Array.zeros(1, 0, 0, 1), Tensors.of(Tensors.empty()));
  }

  public void testInvalid() {
    try {
      Array.zeros(-1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInvalid2() {
    try {
      Array.of(l -> Tensors.vector(l.get(0), l.get(1), l.get(2)), 3, -2, 4);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
