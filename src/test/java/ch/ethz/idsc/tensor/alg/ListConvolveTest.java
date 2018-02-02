// code by jph
package ch.ethz.idsc.tensor.alg;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ListConvolveTest extends TestCase {
  public void testVector1() {
    Tensor kernel = Tensors.vector(0, -1, 3);
    Tensor tensor = ArrayPad.of(Tensors.vector(1, 6, 0, 0, -1), //
        Arrays.asList(kernel.length() - 1), Arrays.asList(kernel.length() - 1));
    Tensor result = ListConvolve.of(kernel, tensor);
    Tensor actual = Tensors.vector(0, -1, -3, 18, 0, 1, -3);
    assertEquals(result, actual);
  }

  public void testMatrix() {
    Tensor kernel = Tensors.fromString("{{2, 1, 3}, {0, 1, -1}}");
    Tensor tensor = Tensors.fromString("{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}");
    Tensor result = ListConvolve.of(kernel, tensor);
    Tensor actual = Tensors.fromString("{{8, 2, -2, -2, 2}, {15, 16, 101, 58, 145}}");
    assertEquals(result, actual);
  }

  public void testLastLayer() {
    Tensor kernel = Tensors.vector(1, -1);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    Tensor result = TensorMap.of(tensor -> ListConvolve.of(kernel, tensor), matrix, TensorRank.of(matrix) - 1);
    assertEquals(Dimensions.of(result), Arrays.asList(2, 4));
  }

  public void testOperator() {
    Tensor kernel = Tensors.vector(1, -1);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    Tensor result = TensorMap.of(ListConvolve.with(kernel), matrix, TensorRank.of(matrix) - 1);
    assertEquals(Dimensions.of(result), Arrays.asList(2, 4));
    assertEquals(result, Tensors.matrixInt(new int[][] { { -1, 2, -3, 1 }, { 1, -2, 4, 0 } }));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Tensor kernel = Tensors.of(Tensors.vector(1, -1));
    UnaryOperator<Tensor> uo = ListConvolve.with(kernel);
    UnaryOperator<Tensor> cp = Serialization.copy(uo);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    Tensor result1 = uo.apply(matrix);
    Tensor result2 = cp.apply(matrix);
    assertEquals(result1, Tensors.matrixInt(new int[][] { { -1, 2, -3, 1 }, { 1, -2, 4, 0 } }));
    assertEquals(result1, result2);
  }

  public void testRankFail() {
    Tensor kernel = Tensors.vector(1, -1);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    try {
      ListConvolve.of(kernel, matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
