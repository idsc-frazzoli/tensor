// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class MaxTest extends TestCase {
  static Tensor max(List<Tensor> col) {
    return col.stream().reduce(Max::of).get();
  }

  public void testColumnwise1() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 1, 3, 3 }, { 2, 2, 7 } });
    Tensor res = matrix.stream().reduce(Entrywise.max()).get();
    Tensor map = MapThread.of(MaxTest::max, matrix.stream().collect(Collectors.toList()), 1);
    assertEquals(res, Tensors.vector(2, 3, 7));
    assertEquals(res, map);
  }

  public void testRowwise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 8, 3, 3 }, { 2, 2, 7 } });
    Tensor res = Tensor.of(matrix.stream().map( //
        row -> row.stream().reduce(Max::of).get()));
    assertEquals(res, Tensors.vector(8, 7));
  }

  public void testElementWise() {
    Tensor tensor = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    Tensor capped = tensor.map(Max.function(RealScalar.ZERO));
    Tensor blub = Tensors.matrixInt(new int[][] { { 0, 3, 0 }, { 2, 0, 7 } });
    assertEquals(capped, blub);
  }

  public void testSet() throws ClassNotFoundException, IOException {
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    ScalarUnaryOperator _op = Max.function(RealScalar.ZERO);
    ScalarUnaryOperator scalarUnaryOperator = Serialization.copy(_op);
    matrix.set(scalarUnaryOperator, Tensor.ALL, 0);
    Tensor blub = Tensors.matrixInt(new int[][] { { 0, 3, -3 }, { 2, -2, 7 } });
    assertEquals(matrix, blub);
  }

  public void testGenericInteger() {
    UnaryOperator<Integer> function = Max.function(100);
    assertEquals(function.apply(50), Integer.valueOf(100));
    assertEquals(function.apply(200), Integer.valueOf(200));
  }

  public void testGenericString() {
    UnaryOperator<String> function = Max.function("math");
    assertEquals(function.apply("library"), "math");
    assertEquals(function.apply("tensor"), "tensor");
  }

  public void testFail() {
    Scalar string = StringScalar.of("string");
    Scalar gauss = GaussScalar.of(1, 3);
    try {
      Max.of(string, gauss);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Max.of(gauss, string);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
