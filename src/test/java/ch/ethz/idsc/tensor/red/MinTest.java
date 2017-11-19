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

public class MinTest extends TestCase {
  static Tensor min(List<Tensor> col) {
    return col.stream().reduce(Min::of).get();
  }

  public void testColumnwise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 1, 3, 3 }, { 2, 2, 7 } });
    Tensor res = MapThread.of(MinTest::min, matrix.flatten(0).collect(Collectors.toList()), 1);
    assertEquals(res, Tensors.vector(1, 2, 3));
  }

  public void testRowwise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 8, 3, 3 }, { 2, 2, 7 } });
    Tensor res = Tensor.of(matrix.flatten(0).map( //
        row -> row.flatten(0).map(Scalar.class::cast).reduce(Min::of).get()));
    assertEquals(res, Tensors.vector(3, 2));
  }

  public void testElementWise() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    Tensor capped = matrix.map(Min.function(RealScalar.ZERO));
    Tensor blub = Tensors.matrixInt(new int[][] { { -8, 0, -3 }, { 0, -2, 0 } });
    assertEquals(capped, blub);
  }

  public void testFail() {
    Scalar string = StringScalar.of("string");
    Scalar gauss = GaussScalar.of(1, 3);
    try {
      Min.of(string, gauss);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Min.of(gauss, string);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
