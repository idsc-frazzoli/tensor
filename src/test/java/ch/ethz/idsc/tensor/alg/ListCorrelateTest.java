// code by jph
package ch.ethz.idsc.tensor.alg;

import java.io.IOException;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ListCorrelateTest extends TestCase {
  public void testVector1() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(0, 0, 1, 0, 0, 0);
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.vector(3, 1, 2, 0);
    assertEquals(result, actual);
  }

  public void testVector2() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(0, 0, 1, 0, -2, 1, 2);
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.vector(3, 1, -4, 1, 3);
    assertEquals(result, actual);
  }

  public void testMatrix() {
    Tensor kernel = Tensors.fromString("{{2, 1, 3}, {0, 1, -1}}");
    Tensor tensor = Tensors.fromString("{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}");
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.fromString("{{2, 2, -2, -2, 2}, {6, 1, -46, 43, 4}}");
    assertEquals(result, actual);
  }

  public void testRank3() {
    Tensor kernel = Tensors.fromString("{{{2, 1, 3}, {0, 1, -1}}}");
    Tensor tensor = Tensors.fromString("{{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}}");
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.fromString("{{{2, 2, -2, -2, 2}, {6, 1, -46, 43, 4}}}");
    assertEquals(result, actual);
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Tensor kernel = Tensors.of(Tensors.vector(1, -1));
    UnaryOperator<Tensor> uo = ListCorrelate.with(kernel);
    UnaryOperator<Tensor> cp = Serialization.copy(uo);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    Tensor result1 = uo.apply(matrix);
    Tensor result2 = cp.apply(matrix);
    assertEquals(result1, Tensors.matrixInt(new int[][] { { 1, -2, 3, -1 }, { -1, 2, -4, 0 } }));
    assertEquals(result1, result2);
  }

  public void testNarrow1() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(4, 5);
    try {
      ListCorrelate.of(kernel, tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNarrow2() {
    Tensor kernel = Tensors.fromString("{{1,2,3}}");
    Tensor tensor = Tensors.fromString("{{1,2}}");
    try {
      ListCorrelate.of(kernel, tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNarrow3() {
    Tensor kernel = Tensors.fromString("{{1,2,3},{2,3,4}}");
    Tensor tensor = Tensors.fromString("{{1,2,3}}");
    try {
      ListCorrelate.of(kernel, tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    Tensor kernel = RealScalar.ZERO;
    Tensor tensor = RealScalar.ONE;
    try {
      ListCorrelate.of(kernel, tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRankFail() {
    Tensor kernel = Tensors.vector(1, -1);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    try {
      ListCorrelate.of(kernel, matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
