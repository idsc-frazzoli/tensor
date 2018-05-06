// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.io.StringScalar;
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
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    Tensor capped = matrix.map(Max.function(RealScalar.ZERO));
    Tensor blub = Tensors.matrixInt(new int[][] { { 0, 3, 0 }, { 2, 0, 7 } });
    assertEquals(capped, blub);
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
