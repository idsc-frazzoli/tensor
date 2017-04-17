// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.MapThread;
import junit.framework.TestCase;

public class MaxTest extends TestCase {
  static Tensor max(List<Tensor> col) {
    return col.stream().reduce(Max::of).get();
  }

  public void testColumnwise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 1, 3, 3 }, { 2, 2, 7 } });
    Tensor res = MapThread.of(MaxTest::max, matrix.flatten(0).collect(Collectors.toList()), 1);
    assertEquals(res, Tensors.vector(2, 3, 7));
  }

  public void testRowwise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 8, 3, 3 }, { 2, 2, 7 } });
    Tensor res = Tensor.of(matrix.flatten(0).map( //
        row -> row.flatten(0).map(Scalar.class::cast).reduce(Max::of).get()));
    assertEquals(res, Tensors.vector(8, 7));
  }

  public void testElementWise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    Tensor capped = matrix.map(Max.with(ZeroScalar.get()));
    Tensor blub = Tensors.matrixInt(new int[][] { { 0, 3, 0 }, { 2, 0, 7 } });
    assertEquals(capped, blub);
  }
}
