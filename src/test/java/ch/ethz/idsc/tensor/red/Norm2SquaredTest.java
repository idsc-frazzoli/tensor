// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Norm2SquaredTest extends TestCase {
  public void testMatrix() {
    Tensor matrix = Tensors.matrix(new Number[][] { { 1, 2, 3 }, { 9, -3, 0 } });
    Scalar nrm = Norm2Squared.ofMatrix(matrix);
    assertEquals(nrm, Norm2Squared.ofMatrix(Transpose.of(matrix)));
    // Mathematica: 9.493062577750756
    Scalar s = DoubleScalar.of(9.493062577750756);
    assertTrue(Chop._14.close(nrm, s.multiply(s)));
  }
}
