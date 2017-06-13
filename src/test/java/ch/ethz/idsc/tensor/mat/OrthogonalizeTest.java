// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import junit.framework.TestCase;

public class OrthogonalizeTest extends TestCase {
  public void testSimple() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1}, {1, 1, 1}}");
    Tensor q = Orthogonalize.of(Transpose.of(matrix));
    // System.out.println(Pretty.of(q));
    assertTrue(OrthogonalMatrixQ.of(q));
  }
}
