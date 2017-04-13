// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class TransposeTest extends TestCase {
  public void testScalar() {
    Tensor v = RealScalar.NEGATIVE_INFINITY;
    try {
      Transpose.of(v, new Integer[] {});
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVector() {
    Tensor v = Tensors.vectorInt(2, 3, 4, 5);
    Tensor r = Transpose.of(v, 0);
    assertEquals(v, r);
  }

  public void testMatrix() {
    // [[0, 1, 2, 3], [4, 5, 6, 7], [8, 9, 10, 11]]
    Tensor m = Tensors.matrix((i, j) -> RationalScalar.of(i * 4 + j, 1), 3, 4);
    Tensor t = Transpose.of(m, 1, 0);
    assertEquals(t.toString(), "[[0, 4, 8], [1, 5, 9], [2, 6, 10], [3, 7, 11]]");
    Tensor r = Transpose.of(m);
    assertEquals(r.toString(), "[[0, 4, 8], [1, 5, 9], [2, 6, 10], [3, 7, 11]]");
  }

  public void testTranspose2() {
    // [[[0, 1, 2], [3, 4, 5]], [[6, 7, 8], [9, 10, 11]]]
    Tensor m = Partition.of(Tensors.matrix((i, j) -> RationalScalar.of(i * 3 + j, 1), 4, 3), 2);
    Tensor t = Transpose.of(m, 0, 1, 2);
    assertEquals(t.toString(), "[[[0, 1, 2], [3, 4, 5]], [[6, 7, 8], [9, 10, 11]]]");
  }

  /** m = Array[0 &, {2, 3, 5}];
   * Dimensions[Transpose[m, {3, 2, 1}]] == {5, 3, 2}
   * Dimensions[Transpose[m, {2, 3, 1}]] == {5, 2, 3} */
  public void testTranspose3() {
    Tensor m = Partition.of(Tensors.matrix((i, j) -> RationalScalar.of(i * 5 + j, 1), 6, 5), 3);
    assertEquals(m.toString(),
        "[[[0, 1, 2, 3, 4], [5, 6, 7, 8, 9], [10, 11, 12, 13, 14]], [[15, 16, 17, 18, 19], [20, 21, 22, 23, 24], [25, 26, 27, 28, 29]]]");
    assertEquals(Dimensions.of(m), Arrays.asList(2, 3, 5));
    {
      Tensor t = Transpose.of(m, 2, 1, 0);
      assertEquals(Dimensions.of(t), Arrays.asList(5, 3, 2));
      assertEquals(t.toString(),
          "[[[0, 15], [5, 20], [10, 25]], [[1, 16], [6, 21], [11, 26]], [[2, 17], [7, 22], [12, 27]], [[3, 18], [8, 23], [13, 28]], [[4, 19], [9, 24], [14, 29]]]");
    }
    {
      Tensor t = Transpose.of(m, 1, 2, 0);
      assertEquals(Dimensions.of(t), Arrays.asList(5, 2, 3));
      assertEquals(t.toString(),
          "[[[0, 5, 10], [15, 20, 25]], [[1, 6, 11], [16, 21, 26]], [[2, 7, 12], [17, 22, 27]], [[3, 8, 13], [18, 23, 28]], [[4, 9, 14], [19, 24, 29]]]");
    }
  }

  public void testModify() {
    Tensor m = Tensors.fromString("[[1,2],[2,4]]");
    Tensor mt = Transpose.of(m);
    mt.set(i -> ZeroScalar.get(), 1, 1);
    assertFalse(m.equals(mt));
    // System.out.println(Pretty.of(m));
    // System.out.println(Pretty.of(mt));
  }

  public void testZeros() {
    assertEquals(Array.zeros(2, 10), Transpose.of(Array.zeros(10, 2)));
  }

  public void testRep() {
    Integer[] input = new Integer[] { 3, 2, 6, 0 };
    int[] copy = Stream.of(input).mapToInt(i -> i).toArray();
    assertEquals(copy[0], 3);
    assertEquals(copy[2], 6);
  }
}
